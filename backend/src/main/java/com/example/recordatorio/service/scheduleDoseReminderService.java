package com.example.recordatorio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.recordatorio.model.Patient; // Importamos el modelo Patient
import com.example.recordatorio.model.Reminder; // Importamos el modelo Reminder
import com.example.recordatorio.model.ScheduleDose; // Importamos el modelo ScheduleDose

import com.example.recordatorio.repository.Ireminder; // Importamos el repositorio Ireminder
import com.example.recordatorio.repository.IscheduleDose; // Importamos el repositorio IscheduleDose

@Service
public class scheduleDoseReminderService {

    @Autowired 
    private IscheduleDose scheduleDoseRepository; // Repositorio de ScheduleDose

    @Autowired
    private Ireminder reminderRepository; // Repositorio de Reminder

    @Autowired
    private EmailService emailService; // Servicio de Email

    // Ejecuta cada 5 minutos para enviar recordatorios si corresponde
@Scheduled(cron = "*/10 * * * * *")
    public void  checkUnconfirmedDoses (){
        List<ScheduleDose> unconfirmedDoses = scheduleDoseRepository.findByIsConfirmed(0); // 0 indica que no ha sido confirmado

        for (ScheduleDose dose : unconfirmedDoses) {
            // Verifica si ya se ha enviado un recordatorio para esta dosis
            Optional<Reminder> existingReminder = reminderRepository.findFirstByDoseID_DoseIDOrderBySendAtAsc(dose.getDoseID());
            LocalDateTime now = LocalDateTime.now();

            if (existingReminder.isPresent()) {
                LocalDateTime firstSendAt = existingReminder.get().getSendAt();
                // Si ya han pasado 9 minutos desde el primer recordatorio, no enviar más
                if (firstSendAt != null && firstSendAt.plusMinutes(9).isBefore(now)) {
                    continue; // No enviar más recordatorios
                }
            }

             // Si no se ha cumplido el tiempo límite, se puede enviar el siguiente recordatorio
             Patient patient = dose.getPatientID(); // Obtener el paciente asociado a la dosis
            String addressMail = patient.getEmail(); // Obtener el correo electrónico del paciente

            // Verificar el permiso de envío de recordatorios
            if(patient.getNotificationPermission() && addressMail != null && !addressMail.isEmpty()){
                emailService.sendDoseReminder(addressMail, dose);
                System.out.println("Recordatorio enviado a: " + addressMail + " para la dosis con ID: " + dose.getDoseID());
            }
        }
    }

    // Ejecuta cada minuto para verificar si es momento de crear una nueva dosis
     @Scheduled(cron = "0 * * * * *")
     public void createNextDoseIfNeeded() {
        List<ScheduleDose> confirmDoses = scheduleDoseRepository.findByIsConfirmed(1); // 1 indica que ha sido confirmado;

        for(ScheduleDose dose : confirmDoses ) {

            // Buscar los reminders de esta dosis
            List <Reminder> reminders = reminderRepository.findByDoseID_DoseID(dose.getDoseID());
            
             // Buscar el último reminder CONFIRMADO (status == true)

             Reminder lastReminder = reminders.stream()
             .filter(reminder -> Boolean.TRUE.equals (reminder.getStatus()))
              .sorted((a, b) -> b.getSendAt().compareTo(a.getSendAt())) // Ordenar por fecha de envío descendente
                .findFirst()
                .orElse(null); // Si no hay recordatorios, lasReminder será null

                if(lastReminder == null)  continue; // Solo para dosis que sí han sido confirmadas

                LocalDateTime LastConfirmation = lastReminder.getSendAt();
                int  freqHours = dose.getMedicationID().getFrequencyHours();
                LocalDateTime nextDoseTime = LastConfirmation.plusMinutes(freqHours);


                if(!LocalDateTime.now().isBefore(nextDoseTime)){
                    // Crear la nueva dosis pendiente para el paciente y medicamento
                    ScheduleDose newDose = new ScheduleDose();
                    newDose.setMedicationID(dose.getMedicationID());
                    newDose.setPatientID(dose.getPatientID());  
                    newDose.setStartDate(nextDoseTime);
                    newDose.setIsConfirmed(0); // 0 indica que no ha sido confirmado
                    newDose.setDurationDays(dose.getDurationDays()); // Copiar la duración de la dosis original


                    ScheduleDose savedDose = scheduleDoseRepository.save(newDose);
                       System.out.println("Nueva dosis creada para el paciente: "
                           + savedDose.getPatientID().getName() + "  y medicamento " + 
                        savedDose.getMedicationID().getName() + " con ID: " + savedDose.getDoseID());   
                }
        }
     }
    
}
