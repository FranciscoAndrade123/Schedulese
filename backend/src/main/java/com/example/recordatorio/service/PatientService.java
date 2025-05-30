package com.example.recordatorio.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.recordatorio.DTO.PatientDTO; // Importamos el DTO PatientDTO
import com.example.recordatorio.model.Patient; // Importamos la entidad Patient
import com.example.recordatorio.repository.Ipatient; // Importamos el repositorio Ipatient

import com.example.recordatorio.DTO.ResponseDTO; // Importamos el DTO ResponseDTO


@Service
public class PatientService {

    @Autowired
    private Ipatient dataRepository; // Inyectamos el repositorio Ipatient


    // Método para GUARDAR  los DATOS de los pacientes
    public ResponseDTO save(PatientDTO patientDTO) {

        // Validaciónes 
        if (patientDTO == null) {  
            return new ResponseDTO("El paciente no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getName() == null || patientDTO.getName().trim().isEmpty()) {
            return new ResponseDTO("El nombre no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getName().matches(".*\\d.*")) {
            return new ResponseDTO("El nombre no puede contener números", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getEmail() == null || patientDTO.getEmail().trim().isEmpty()) {
            return new ResponseDTO("El correo electrónico no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }

        // Convertimos el DTO a modelo
        Patient patient = convertToModel(patientDTO);
        
        // Guardamos el paciente en la base de datos
        dataRepository.save(patient);

        return new ResponseDTO("Paciente guardado exitosamente", HttpStatus.OK.toString());
    }

    // Método para OBTENER todos los pacientes
    public List<Patient>findAll() {
        return dataRepository.findAll();
    }

    // Método para OBTENER un paciente por su ID
    public Optional<Patient> findById(int id) {
        return dataRepository.findById(id);
    }

    // Método para ELIMINAR un paciente por su ID
    public ResponseDTO deleteById(int id) {
        // Verificamos si el paciente existe
        if (!dataRepository.existsById(id)) {
            return new ResponseDTO("Paciente no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        
        // Eliminamos el paciente
        dataRepository.deleteById(id);
        return new ResponseDTO("Paciente eliminado exitosamente", HttpStatus.OK.toString());
    }

    // Método para ACTUALIZAR un paciente
    public ResponseDTO update(int id, PatientDTO patientDTO) {
        // Validación
        Patient existingPatient = dataRepository.findById(id).orElse(null);
        if (existingPatient == null) {
            return new ResponseDTO("Paciente no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        if (patientDTO == null) {
            return new ResponseDTO("El paciente no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getName() == null || patientDTO.getName().trim().isEmpty()) {
            return new ResponseDTO("El nombre no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getName().matches(".*\\d.*")) {
            return new ResponseDTO("El nombre no puede contener números", HttpStatus.BAD_REQUEST.toString());
        }
        if (patientDTO.getEmail() == null || patientDTO.getEmail().trim().isEmpty()) {
            return new ResponseDTO("El correo electrónico no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }
        // Convertimos el DTO a modelo, usando el ID correcto
        Patient patient = new Patient(
            id,
            patientDTO.getName(),
            patientDTO.getEmail(),
            patientDTO.getNotificationPermission()
        );

        // Actualizamos el paciente
        dataRepository.save(patient);
        return new ResponseDTO("Paciente actualizado exitosamente", HttpStatus.OK.toString());
    }

    //Filtrar pacientes por nombre del paciente
    public List <Patient> getListPatientForName(String filter){
         return dataRepository.getListPatientForName(filter);
     }

    //Convertir de modelo a DTO
    public PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
            patient.getName(), 
            patient.getEmail(), 
            patient.getNotificationPermission());
    }

    //Convertir de DTO a modelo
    public Patient convertToModel(PatientDTO patientDTO) {
        return new Patient(
            0, // El ID se asignará automáticamente al guardar
            patientDTO.getName(), 
            patientDTO.getEmail(), 
            patientDTO.getNotificationPermission());
    }
    
}
