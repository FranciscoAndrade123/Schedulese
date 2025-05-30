package com.example.recordatorio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.recordatorio.DTO.ScheduleDoseDTO;// DTO de ScheduleDose
import com.example.recordatorio.model.Medication;
import com.example.recordatorio.model.Patient;
import com.example.recordatorio.model.ScheduleDose; // Entidad ScheduleDose

import com.example.recordatorio.DTO.ResponseDTO; // Respuesta genérica

import com.example.recordatorio.repository.IscheduleDose; // Repositorio de ScheduleDose
import com.example.recordatorio.repository.Imedication; // Repositorio de Medication
import com.example.recordatorio.repository.Ipatient; // Repositorio de Patient

@Service
public class ScheduleDoseService {

    @Autowired
    private IscheduleDose scheduleDoseRepository; // Repositorio de ScheduleDose

    @Autowired
    private Imedication medicationRepository; // Repositorio de Medication

    @Autowired
    private Ipatient patientRepository; // Repositorio de Patient


    // Método para GUARDAR los DATOS de ScheduleDose
    public ResponseDTO save(ScheduleDoseDTO scheduleDoseDTO) {

        // Validaciones
        if (scheduleDoseDTO == null) {
            return new ResponseDTO("El ScheduleDose no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getMedicationID() <= 0) {
            return new ResponseDTO("El ID de medicamento debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getPatientID() <= 0) {
            return new ResponseDTO("El ID de paciente debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getStartDate() == null) {
            return new ResponseDTO("La fecha de inicio no puede ser nula", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getDurationDays() <= 0) {
            return new ResponseDTO("La duración en días debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
        }

        // Buscamos el medicamento y el paciente por sus IDs
        Optional<Medication> medicationOpt = medicationRepository.findById(scheduleDoseDTO.getMedicationID());
        Optional<Patient> patientOpt = patientRepository.findById(scheduleDoseDTO.getPatientID());

        if (!medicationOpt.isPresent()) {
            return new ResponseDTO("Medicamento no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        if (!patientOpt.isPresent()) {
            return new ResponseDTO("Paciente no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        // Convertimos el DTO a modelo
        ScheduleDose scheduleDose = converToModel(scheduleDoseDTO, medicationOpt.get(), patientOpt.get());

        // Guardamos el ScheduleDose en la base de datos
        scheduleDoseRepository.save(scheduleDose);

        return new ResponseDTO("ScheduleDose guardado exitosamente", HttpStatus.OK.toString());
    }

    // Método para OBTENER todos los ScheduleDose
    public List<ScheduleDose> findAll() {
        return scheduleDoseRepository.findAll();
    }

    // Método para OBTENER un ScheduleDose por su ID
    public Optional<ScheduleDose> findById(int id) {
        return scheduleDoseRepository.findById(id);
    }

    // Método para ELIMINAR un ScheduleDose por su ID
    public ResponseDTO deleteById(int id) {
        // Verificamos si el ScheduleDose existe
        if (!scheduleDoseRepository.existsById(id)) {
            return new ResponseDTO("ScheduleDose no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        // Eliminamos el ScheduleDose
        scheduleDoseRepository.deleteById(id);
        return new ResponseDTO("ScheduleDose eliminado exitosamente", HttpStatus.OK.toString());
    }

    // Método para ACTUALIZAR un ScheduleDose
    public ResponseDTO update(int id, ScheduleDoseDTO scheduleDoseDTO) {
        // Verificamos si el ScheduleDose existe
        if (!scheduleDoseRepository.existsById(id)) {
            return new ResponseDTO("ScheduleDose no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        // Validaciones
        if (scheduleDoseDTO == null) {
            return new ResponseDTO("El ScheduleDose no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getMedicationID() <= 0) {
            return new ResponseDTO("El ID de medicamento debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
        }
        if (scheduleDoseDTO.getPatientID() <=0) {
                return new ResponseDTO("El ID de paciente debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
            }

        if (scheduleDoseDTO.getStartDate() == null) {
            return new ResponseDTO("La fecha de inicio no puede ser nula", HttpStatus.BAD_REQUEST.toString());
        }

        if (scheduleDoseDTO.getDurationDays() <= 0) {
            return new ResponseDTO("La duración en días debe ser mayor que cero", HttpStatus.BAD_REQUEST.toString());
        }

        // Buscamos el ScheduleDose existente
        Optional<ScheduleDose> existingScheduleDoseOpt = scheduleDoseRepository.findById(id);
        if (!existingScheduleDoseOpt.isPresent()) {
            return new ResponseDTO("ScheduleDose no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        ScheduleDose existingScheduleDose = existingScheduleDoseOpt.get();

        // Buscamos el medicamento y el paciente por sus IDs
        Optional<Medication> medicationOpt = medicationRepository.findById(scheduleDoseDTO.getMedicationID());
        Optional<Patient> patientOpt = patientRepository.findById(scheduleDoseDTO.getPatientID());
        if (!medicationOpt.isPresent()) {
            return new ResponseDTO("Medicamento no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        if (!patientOpt.isPresent()) {
            return new ResponseDTO("Paciente no encontrado", HttpStatus.NOT_FOUND.toString());
        }
        // Actualizamos el ScheduleDose
        existingScheduleDose.setMedicationID(medicationOpt.get());
        existingScheduleDose.setPatientID(patientOpt.get());
        existingScheduleDose.setStartDate(scheduleDoseDTO.getStartDate());
        existingScheduleDose.setIsConfirmed(scheduleDoseDTO.getIsConfirmed());
        existingScheduleDose.setDurationDays(scheduleDoseDTO.getDurationDays());

        // Guardamos el ScheduleDose actualizado en la base de datos
        scheduleDoseRepository.save(existingScheduleDose);
        return new ResponseDTO("ScheduleDose actualizado exitosamente", HttpStatus.OK.toString());
    }

    





    //Conversion de DTO a modelo
    public ScheduleDose converToModel (ScheduleDoseDTO scheduleDoseDTO , Medication medication , Patient patient){
        ScheduleDose scheduleDose = new ScheduleDose();
        scheduleDose.setMedicationID(medication);
        scheduleDose.setPatientID(patient);
        scheduleDose.setStartDate(scheduleDoseDTO.getStartDate());
        scheduleDose.setIsConfirmed(scheduleDoseDTO.getIsConfirmed());
        scheduleDose.setDurationDays(scheduleDoseDTO.getDurationDays());
        return scheduleDose;
    }

    //Conversion de modelo a DTO
    public ScheduleDoseDTO convertToDTO(ScheduleDose scheduleDose) {
        ScheduleDoseDTO scheduleDoseDTO = new ScheduleDoseDTO();
        scheduleDoseDTO.setMedicationID(scheduleDose.getMedicationID().getMedicationID());
        scheduleDoseDTO.setPatientID(scheduleDose.getPatientID().getPatientID());
        scheduleDoseDTO.setStartDate(scheduleDose.getStartDate());
        scheduleDoseDTO.setIsConfirmed(scheduleDose.getIsConfirmed());
        scheduleDoseDTO.setDurationDays(scheduleDose.getDurationDays());
        return scheduleDoseDTO;
    }
    
}
