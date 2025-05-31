package com.example.recordatorio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recordatorio.model.Reminder;
import com.example.recordatorio.model.ScheduleDose;

import com.example.recordatorio.repository.IscheduleDose;

import com.example.recordatorio.service.EmailService;
import com.example.recordatorio.service.ReminderService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private IscheduleDose scheduleDoseRepository;

    @Autowired
    private ReminderService reminderService;

    /**
     * Endpoint que envía el recordatorio de dosis al paciente.
     * Este es un ejemplo de uso; en producción, llama este método desde el servicio programado.
     */
    
     @GetMapping("/sendDoseReminder/{email}/{doseId}")
     public String sendoseReminder(@PathVariable String email, @PathVariable Integer doseId) {
        Optional<ScheduleDose> scheduleDoseOptional = scheduleDoseRepository.findById(doseId);
        if(!scheduleDoseOptional.isPresent()) {
            return "Schedule dose not found for ID: " + doseId;
        }
        emailService.sendDoseReminder(email, scheduleDoseOptional.get());
        return "Mail sent successfully";
     }

     
    /**
     * Endpoint que confirma la toma de la dosis.
     * El enlace ahora recibe el reminderId como parámetro.
     * Ejemplo de confirmUrl: https://tudominio.com/email/confirmDose/456
     */

     @GetMapping("/confirmDose/{reminderId}")
     public String confirmDose(@PathVariable Integer reminderId) {

        //1. Marcar el reminder como confirmado
        reminderService.maracarReminderAsSent(reminderId);

        // 2.  También actualizar 'confirmationStatus' en la dosis relacionada
        Reminder reminder = reminderService.findReminderById(reminderId);
        if (reminder != null) {
            ScheduleDose scheduleDose = reminder.getDoseID();
            scheduleDose.setIsConfirmed(1); // o setConfirmationStatus(1) según tu modelo
            scheduleDoseRepository.save(scheduleDose);
            return "Dosis confirmada correctamente";
        } else {
            return "Recordatorio no encontrado";
        }

     }
}
