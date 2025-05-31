package com.example.recordatorio.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import com.example.recordatorio.DTO.ResponseDTO;
import com.example.recordatorio.model.Medication; // Importamos el modelo Medication
import com.example.recordatorio.model.Reminder; // Importamos el modelo Reminder
import com.example.recordatorio.model.ScheduleDose; // Importamos el modelo ScheduleDose

import com.example.recordatorio.repository.Ireminder; // Importamos el repositorio Ireminder

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private ReminderService reminderService; // Importamos el servicio ReminderService

    @Autowired
    private Ireminder reminderRepository; // Importamos el repositorio Ireminder

    /**
     * Envía un correo de recordatorio de dosis con botón de confirmación.
     * @param addressMail Correo electrónico del paciente
     * @param dose Instancia de la dosis agendada
     */

     public void sendDoseReminder(String addressMail, ScheduleDose dose) {
      
        try {
            // Lógica para evitar el tercer reminder si han pasado más de 9 minutos desde el primer reminder
            Optional<Reminder> firstReminder = reminderRepository.findFirstByDoseID_DoseIDOrderBySendAtAsc(dose.getDoseID());
            LocalDateTime now = LocalDateTime.now();

            if(firstReminder.isPresent()){
                LocalDateTime firstSendAt = firstReminder.get().getSendAt();
                if (firstSendAt!= null && firstSendAt.plusMinutes(9).isBefore(now)) {
                      // Ya pasaron 9 minutos desde el primer reminder, no enviar más correos
                    return;
                }
            }

            Medication medication = dose.getMedicationID();
            String medicationName = medication.getName();
            String medicationDosage = medication.getDosage();   

            //Crea un nuevo reminder y obtiene el ID
            Reminder newReminder = reminderService.createNewReminder(dose);

            if (newReminder == null) {
                // Maneja el caso de error si la dosis no existe
                return;
            }

            String subject = "Recordatorio de medicamento: ";

            // Enlace de confirmación usa el reminderId (no el doseId)
            String confirmUrl = "http://localhost:8080/email/confirmDose/" + newReminder.getReminderID();

            String bodyMail = 
            "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Recordatorio de Medicamento</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }\n" +
                "        .container { background-color: #fff; max-width: 500px; margin: 40px auto; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }\n" +
                "        .header { text-align: center; color: #34495e; }\n" +
                "        .content { color: #353b48; line-height: 1.6; font-size: 16px; }\n" +
                "        .button {\n" +
                "            display: inline-block; margin: 20px 0 10px 0; padding: 14px 24px; background-color: #27ae60; color: white;\n" +
                "            text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px;\n" +
                "        }\n" +
                "        .footer { margin-top: 30px; font-size: 13px; color: #888; text-align: center; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                 "    <div class=\"container\">\n" +
                "        <h2 class=\"header\">¡Es hora de tomar tu medicamento!</h2>\n" +
                "        <div class=\"content\">\n" +
                "            <p><b>Nombre:</b> " + medicationName + "</p>\n" +
                "            <p><b>Dosis:</b> " + medicationDosage + "</p>\n" +
                "            <p>Por favor, recuerda tomar tu medicamento según la indicación.</p>\n" +
                "            <a href=\"" + confirmUrl + "\" class=\"button\">Confirmar que ya tomé mi dosis</a>\n" +
                "            <p>Una vez confirmes, no te volveremos a enviar recordatorios para esta dosis.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">Este correo es automático, no responder.</div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

                emailSender(addressMail, subject, bodyMail);

        }catch (Exception e) {
           System.out.println(e.getMessage());
        }
     }

      public boolean emailSender(String addressMail, String subject, String bodyMail) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(addressMail);
            helper.setSubject(subject);
            helper.setText(bodyMail, true);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    

    }
