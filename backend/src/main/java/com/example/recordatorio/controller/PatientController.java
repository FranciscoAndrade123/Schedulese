package com.example.recordatorio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recordatorio.DTO.PatientDTO;
import com.example.recordatorio.model.Patient;
import com.example.recordatorio.service.PatientService;

import com.example.recordatorio.DTO.ResponseDTO;


@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    
    @Autowired
    private PatientService patientService; // Inyectamos el servicio PatientService

    //El de POST es para crear un paciente
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        ResponseDTO response = patientService.save(patientDTO);
         HttpStatus httpStatus = response.getStatus().equals(HttpStatus.OK.toString()) 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;
            
        return new ResponseEntity<>(response, httpStatus);    
    }

    //El de GET es para obtener todos los pacientes
    @GetMapping("/")
    public List<Patient>  getAllPatients() {
        return patientService.findAll();
    }

    // El de GET es para obtener un paciente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPacienteById(@PathVariable int id) {
        Optional<Patient> paciente = patientService.findById(id);
        if (paciente.isPresent()) {
            // Puedes devolver el paciente directamente o convertirlo a DTO
            return ResponseEntity.ok(paciente.get());
        } else {
            // Si no se encuentra el paciente, devolvemos un error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // El de DELETE es para eliminar un paciente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePatient(@PathVariable int id) {
        Optional<Patient> paciente = patientService.findById(id);
        if (paciente.isPresent()) {
            ResponseDTO response = patientService.deleteById(id);
            return ResponseEntity.ok(response); // Devuelve mensaje de éxito
        } else {
            ResponseDTO response = new ResponseDTO("Paciente no encontrado", HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Devuelve mensaje de error
        }
    }

    // El de PUT es para actualizar un paciente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable int id, @RequestBody PatientDTO patientDTO) {
        var message = patientService.update( id ,patientDTO);
        return new ResponseEntity<>(message, 
            message.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // El de GET es para obtener pacientes por nombre
    @GetMapping("/filter/{name}")
    public List<Patient> getPatientsByName(@PathVariable String name) {
        return patientService.getListPatientForName(name);
    }
}
