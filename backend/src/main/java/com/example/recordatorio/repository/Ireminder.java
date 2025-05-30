package com.example.recordatorio.repository;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recordatorio.model.Reminder; // Importamos el modelo Reminder


public interface Ireminder  extends JpaRepository<Reminder, Integer> {

     List<Reminder> findByDoseID_DoseID(Integer doseID);

    // Nuevo método para encontrar el primer Reminder (más antiguo) de una dosis
    //Optional<Reminder> findFirstByDoseID_DoseIDOrderBySendAtAsc(Integer doseID);
    
}
