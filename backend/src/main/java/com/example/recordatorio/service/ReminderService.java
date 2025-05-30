package com.example.recordatorio.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.recordatorio.DTO.ReminderDTO; // Importamos el DTO ReminderDTO
import com.example.recordatorio.model.Reminder; // Importamos la entidad Reminder
import com.example.recordatorio.model.ScheduleDose; // Importamos la entidad ScheduleDose

import com.example.recordatorio.DTO.ResponseDTO; // Importamos el DTO ResponseDTO

import com.example.recordatorio.repository.Ireminder; // Importamos el repositorio Ireminder
import com.example.recordatorio.repository.IscheduleDose; // Importamos el repositorio IScheduleD

@Service
public class ReminderService {

    @Autowired
    private Ireminder reminderRepository; // Inyectamos el repositorio Ireminder

    @Autowired
    private IscheduleDose scheduleDoseRepository; // Inyectamos el repositorio IScheduleDose

    
   // Método para crear y guardar un nuevo recordatorio asociado a una dosis
    public ResponseDTO createNewReminder(int doseId) {

    // Buscar la dosis por su ID
    Optional<ScheduleDose> doseOpt = scheduleDoseRepository.findById(doseId);
    if (!doseOpt.isPresent()) {
        return new ResponseDTO("Dosis no encontrada", HttpStatus.NOT_FOUND.toString());
    }

    // Crear el recordatorio
    Reminder reminder = new Reminder();
    reminder.setDoseID(doseOpt.get());
    reminder.setSendAt(LocalDateTime.now());
    reminder.setStatus(0); // 0 = pendiente

    // Guardar el recordatorio
    reminderRepository.save(reminder);

    return new ResponseDTO("Recordatorio creado exitosamente", HttpStatus.OK.toString());
}

     // Marcar reminder como confirmado por ID
        public void maracarReminderAsSent(int reminderId) {
        // Buscar el recordatorio por su ID
        Optional<Reminder> reminderOpt = reminderRepository.findById(reminderId);
        if (reminderOpt.isPresent()) {
            Reminder reminder = reminderOpt.get();
            // Actualizar el estado del recordatorio a enviado (1)
            reminder.setStatus(1);
            // Guardar los cambios en la base de datos
            reminderRepository.save(reminder);
        } else {
            throw new RuntimeException("Recordatorio no encontrado con ID: " + reminderId);
        }
    }

    // Listar reminders por dosis (historial)
    public List<Reminder> findRemindersByDoseId(int doseId) {
        return reminderRepository.findByDoseID_DoseID(doseId);
    }


    // Método para guardar un nuevo recordatorio
    public ResponseDTO save(ReminderDTO reminderDTO) {
        // Buscamos la ScheduleDose por su ID
        Optional<ScheduleDose> scheduleDoseOpt = scheduleDoseRepository.findById(reminderDTO.getDoseID());
        if (!scheduleDoseOpt.isPresent()) {
            return new ResponseDTO("No existe la dosis asociada", HttpStatus.NOT_FOUND.toString());
        }
        // Convertimos el DTO a modelo Reminder
        Reminder reminder = convertToModel(reminderDTO, scheduleDoseOpt.get());

        // Guardamos el recordatorio
        reminderRepository.save(reminder);

        return new ResponseDTO("Recordatorio guardado exitosamente", HttpStatus.OK.toString());
    }

    // Método para obtener todos los recordatorios
    public List<Reminder> findAllReminders() {
        return reminderRepository.findAll();
    }

    // Método para obtener un recordatorio por su ID
    public Reminder findReminderById(int reminderId) {
        Optional<Reminder> reminderOpt = reminderRepository.findById(reminderId);
        if (reminderOpt.isPresent()) {
            return reminderOpt.get();
        } else {
            return null; // O lanzar una excepción si prefieres
        }
    }

    // Método para eliminar un recordatorio por su ID
    public ResponseDTO deleteReminder(int reminderId) {
        // Buscamos el recordatorio por su ID
        Optional<Reminder> reminderOpt = reminderRepository.findById(reminderId);
        if (!reminderOpt.isPresent()) {
            return new ResponseDTO("Recordatorio no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        // Eliminamos el recordatorio
        reminderRepository.deleteById(reminderId);
        return new ResponseDTO("Recordatorio eliminado exitosamente", HttpStatus.OK.toString());
    }


    // Método para actualizar un recordatorio
    public ResponseDTO update(int id , ReminderDTO reminderDTO) {

        // Buscamos el recordatorio por su ID (reminderID)
        Optional <Reminder> reminderOpt = reminderRepository.findById(id);
        if (!reminderOpt.isPresent()) {
            return new ResponseDTO("Recordatorio no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        // Buscamos la ScheduleDose por su ID
        Optional<ScheduleDose> scheduleDoseOpt = scheduleDoseRepository.findById(reminderDTO.getDoseID());
        if (!scheduleDoseOpt.isPresent()) {
            return new ResponseDTO("No existe la dosis asociada", HttpStatus.NOT_FOUND.toString());
        }

        // Convertimos el DTO a modelo Reminder
        Reminder reminder = convertToModel(reminderDTO, scheduleDoseOpt.get());
        reminder.setReminderID(id); // Asignamos el ID del recordatorio a actualizar

        // Guardamos el recordatorio actualizado
        reminderRepository.save(reminder);
        return new ResponseDTO("Recordatorio actualizado exitosamente", HttpStatus.OK.toString());
    }


    // De DTO a modelo Reminder
    public Reminder convertToModel(ReminderDTO reminderDTO , ScheduleDose scheduleDose) {
        Reminder reminder = new Reminder();
        reminder.setReminderID(0); // Inicializamos con 0, 
        reminder.setSendAt(reminderDTO.getSendAt());
        reminder.setStatus(reminderDTO.getStatus());
        reminder.setDoseID(scheduleDose); // Asignamos el ScheduleDose al Reminder
        
        return reminder;
    }

    // De modelo Reminder a DTO
    public ReminderDTO convertToDTO(Reminder reminder) {
        ReminderDTO reminderDTO = new ReminderDTO();
        reminderDTO.setDoseID(reminder.getDoseID().getDoseID()); // Asignamos el ID de ScheduleDose
        reminderDTO.setSendAt(reminder.getSendAt());
        reminderDTO.setStatus(reminder.getStatus());
        
        return reminderDTO;
    }
    
}
