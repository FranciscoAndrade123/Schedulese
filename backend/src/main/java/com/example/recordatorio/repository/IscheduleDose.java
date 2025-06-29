package com.example.recordatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recordatorio.model.ScheduleDose;

@Repository
public interface IscheduleDose extends JpaRepository <ScheduleDose, Integer> {
    
    List<ScheduleDose> findByIsConfirmed(int isConfirmed);
    
}
