package com.example.recordatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.recordatorio.model.Medication; // Importamos la entidad Medication

@Repository
public interface Imedication extends JpaRepository<Medication, Integer> {

    // Esta interfaz hereda de JpaRepository, lo que proporciona métodos CRUD básicos para la entidad Medication

     //este funcion es para obtener mediante filtraciones el nombre de la medicación
    @Query("SELECT me FROM medication me WHERE me.name LIKE %?1%")
    List<Medication> getListMedicationForName(String filter);
    
}
