package com.example.recordatorio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recordatorio.DTO.ScheduleDoseDTO; // DTO de ScheduleDose 
import com.example.recordatorio.model.ScheduleDose; // Entidad ScheduleDose
import com.example.recordatorio.service.ScheduleDoseService; // Servicio de ScheduleDose

import com.example.recordatorio.DTO.ResponseDTO; // Respuesta genérica

@RestController
@RequestMapping("/api/v1/scheduledoses")    
public class ScheduleDoseController {

    @Autowired
    private ScheduleDoseService scheduleDoseService; // Inyectamos el servicio ScheduleDoseService

    // Método para crear un ScheduleDose
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createScheduleDose(@RequestBody ScheduleDoseDTO scheduleDoseDTO) {
        ResponseDTO response = scheduleDoseService.save(scheduleDoseDTO);
        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
      
        return new ResponseEntity<>(response, httpStatus);
    }

    // Método para obtener todos los ScheduleDoses
    @GetMapping("/")
    public List<ScheduleDose> getAllScheduleDoses() {
        return scheduleDoseService.findAll();
    }

    // Método para obtener un ScheduleDose por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDose> getScheduleDoseById(@PathVariable int id) {
        Optional<ScheduleDose> scheduleDose = scheduleDoseService.findById(id);
        if (scheduleDose.isPresent()) {
            return ResponseEntity.ok(scheduleDose.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para eliminar un ScheduleDose por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteScheduleDose(@PathVariable int id) {
        Optional<ScheduleDose> scheduleDose = scheduleDoseService.findById(id);
        if (scheduleDose.isPresent()) {
            ResponseDTO response = scheduleDoseService.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para actualizar un ScheduleDose
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateScheduleDose(@PathVariable int id, @RequestBody ScheduleDoseDTO scheduleDoseDTO) {
        ResponseDTO response = scheduleDoseService.update(id,scheduleDoseDTO);
        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
        
        return new ResponseEntity<>(response, httpStatus);
    }
    
}
