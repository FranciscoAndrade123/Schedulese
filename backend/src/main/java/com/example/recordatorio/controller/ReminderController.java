package com.example.recordatorio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recordatorio.DTO.ReminderDTO; // Importamos el DTO ReminderDTO
import com.example.recordatorio.model.Reminder; // Importamos la entidad Reminder
import com.example.recordatorio.service.ReminderService; // Importamos el servicio ReminderService

import com.example.recordatorio.DTO.ResponseDTO; // Importamos el DTO ResponseDTO

@RestController
@RequestMapping("/api/v1/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService; // Inyectamos el servicio ReminderService

    // Endpoint para crear un nuevo recordatorio asociado a una dosis
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createReminder(@RequestBody ReminderDTO reminderDTO) {
        ResponseDTO response = reminderService.save(reminderDTO);

        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;

            return new ResponseEntity<>(response, httpStatus);
    }

    // Endpoint para obtener todos los recordatorios
    @GetMapping("/")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        List<Reminder> reminders = reminderService.findAllReminders();
        return ResponseEntity.ok(reminders);
    }

    // Endpoint para obtener un recordatorio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable int id) {
        Optional<Reminder> reminder = Optional.ofNullable(reminderService.findReminderById(id));
        if (reminder.isPresent()) {
            return ResponseEntity.ok(reminder.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para eliminar un recordatorio
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteReminder(@PathVariable int id) {
        Optional<Reminder> reminder = Optional.ofNullable(reminderService.findReminderById(id));
        if (reminder.isPresent()) {
            ResponseDTO response = reminderService.deleteReminder(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para actualizar un recordatorio
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateReminder(@PathVariable int id, @RequestBody ReminderDTO reminderDTO) {
        ResponseDTO response = reminderService.update(id, reminderDTO);
        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, httpStatus);
    }
    
}
