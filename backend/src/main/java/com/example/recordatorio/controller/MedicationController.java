package com.example.recordatorio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recordatorio.DTO.MedicationDTO;
import com.example.recordatorio.model.Medication;
import com.example.recordatorio.service.MedicationService;

import com.example.recordatorio.DTO.ResponseDTO;

@RestController
@RequestMapping("/api/v1/medications")
public class MedicationController {
    @Autowired
    private MedicationService medicationService; // Inyectamos el servicio MedicationService

    // El de POST es para crear un medicamento
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createMedication(@RequestBody MedicationDTO medicationDTO) {
        ResponseDTO response = medicationService.save(medicationDTO);
        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
            
        return new ResponseEntity<>(response, httpStatus);    
    }

    // El de GET es para obtener todos los medicamentos
    @GetMapping("/")
    public List<Medication> getAllMedications() {
        return medicationService.findAll();
    }

    // El de GET es para obtener un medicamento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable int id) {
        Optional<Medication> medication = medicationService.findById(id);
        if (medication.isPresent()) {
            return ResponseEntity.ok(medication.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // El de DELETE es para eliminar un medicamento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteMedication(@PathVariable int id) {
        Optional<Medication> medication = medicationService.findById(id);
        if (medication.isPresent()) {
            ResponseDTO response = medicationService.eliminar(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // El de PUT es para actualizar un medicamento por su ID
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateMedication(@PathVariable int id, @RequestBody MedicationDTO medicationDTO) {
        ResponseDTO response = medicationService.update(id, medicationDTO);
        HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
            
        return new ResponseEntity<>(response, httpStatus);    
    }

    // El de GET es para filtrar medicamentos por nombre
    @GetMapping("/filter/{filter}") 
    public List<Medication> filterMedicationsByName(@PathVariable String filter) {
        return medicationService.findByName(filter);
    }
    
}
