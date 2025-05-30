package com.example.recordatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.recordatorio.model.Patient; // Importamos la entidad Patient

@Repository
public interface Ipatient extends JpaRepository<Patient, Integer>{
    // Esta interfaz hereda de JpaRepository, lo que proporciona métodos CRUD básicos para la entidad Patient

     //este funcion es para obtener mediante filtraciones el nombre del paciente
    @Query("SELECT pa FROM patient pa WHERE pa.name LIKE %?1%")
    List<Patient> getListPatientForName(String filter);

} 
