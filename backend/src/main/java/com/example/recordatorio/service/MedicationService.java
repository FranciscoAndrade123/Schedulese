package com.example.recordatorio.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.recordatorio.DTO.MedicationDTO; // Importamos el DTO MedicationDTO
import com.example.recordatorio.model.Medication; // Importamos la entidad Medication
import com.example.recordatorio.repository.Imedication; // Importamos el repositorio IMedicationRepository

import com.example.recordatorio.DTO.ResponseDTO; // Importamos el DTO ResponseDTO



@Service
public class MedicationService {

    @Autowired
    private Imedication dataRepository; // Inyectamos el repositorio IMedicationRepository


    // Método para GUARDAR los DATOS de los medicamentos
    public ResponseDTO save(MedicationDTO medicationDTO) {

        // Validaciones 
        if (medicationDTO == null) {  
            return new ResponseDTO("El medicamento no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        if (medicationDTO.getName() == null || medicationDTO.getName().trim().isEmpty()) {
            return new ResponseDTO("El nombre no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }
        if (medicationDTO.getDosage() == null || medicationDTO.getDosage().trim().isEmpty()) {
            return new ResponseDTO("La dosis no puede ser nula o vacía", HttpStatus.BAD_REQUEST.toString());
        }
        if (medicationDTO.getfrequencyHours() <= 0) {
            return new ResponseDTO("La frecuencia en horas debe ser mayor a cero", HttpStatus.BAD_REQUEST.toString());
        }

        // Convertimos el DTO a modelo
        Medication medication = convertToModel(medicationDTO);
        
        // Guardamos el medicamento en la base de datos
        dataRepository.save(medication);

        return new ResponseDTO("Medicamento guardado exitosamente", HttpStatus.OK.toString());
    }

    // Método para OBTENER todos los medicamentos
    public List<Medication> findAll() {
        return dataRepository.findAll();
    }

    // Método para OBTENER un medicamento por su ID
    public Optional<Medication> findById(int id) {
        return dataRepository.findById(id);
    }

    // Método para filtrar medicamentos por nombre
    public List<Medication> findByName(String name) {
        return dataRepository.getListMedicationForName(name);
    }

    // Método para ELIMINAR un medicamento por su ID
    public ResponseDTO eliminar(int id) {
        dataRepository.deleteById(id);
        return new ResponseDTO("Medicamento eliminado exitosamente", HttpStatus.OK.toString());
    }

    // Método para ACTUALIZAR un medicamento
    public ResponseDTO update(int id, MedicationDTO medicationDTO) {
        // Validaciones 
        if (medicationDTO == null) {  
            return new ResponseDTO("El medicamento no puede ser nulo", HttpStatus.BAD_REQUEST.toString());
        }
        
        if (medicationDTO.getName() == null || medicationDTO.getName().trim().isEmpty()) {
            return new ResponseDTO("El nombre no puede ser nulo o vacío", HttpStatus.BAD_REQUEST.toString());
        }
        if (medicationDTO.getDosage() == null || medicationDTO.getDosage().trim().isEmpty()) {
            return new ResponseDTO("La dosis no puede ser nula o vacía", HttpStatus.BAD_REQUEST.toString());
        }
        if (medicationDTO.getfrequencyHours() <= 0) {
            return new ResponseDTO("La frecuencia en horas debe ser mayor a cero", HttpStatus.BAD_REQUEST.toString());
        }

        // Verificamos si el medicamento existe
        if (!dataRepository.existsById(id)) {
            return new ResponseDTO("Medicamento no encontrado", HttpStatus.NOT_FOUND.toString());
        }

        // Convertimos el DTO a modelo
        Medication medication = new Medication(
            id,
            medicationDTO.getName(),
            medicationDTO.getDosage(),
            medicationDTO.getfrequencyHours()
        );
        
        // Actualizamos el medicamento en la base de datos
        dataRepository.save(medication);

        return new ResponseDTO("Medicamento actualizado exitosamente", HttpStatus.OK.toString());
    }


    //Convertir de modelo a DTO
    //private MedicationDTO convertToDTO(Medication medication) {
    //MedicationDTO medicationDTO = new MedicationDTO();
    //medicationDTO.setName(medication.getName());
    //medicationDTO.setDosage(medication.getDosage());
    //medicationDTO.setfrequencyHours(medication.getFrequencyHours());
    //return medicationDTO;
    //}

    //Convertir de DTO a modelo
    private Medication convertToModel(MedicationDTO medicationDTO) {
    Medication medication = new Medication();
    medication.setMedicationID(0);
    medication.setName(medicationDTO.getName());
    medication.setDosage(medicationDTO.getDosage());
    medication.setFrequencyHours(medicationDTO.getfrequencyHours());
    return medication;
    }

    
}
