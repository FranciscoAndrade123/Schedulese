package com.example.recordatorio.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity (name="ScheduleDose")

public class ScheduleDose {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="doseID") 
    private int doseID ;

    //Relacion con la entidad Medication
    @ManyToOne
    @JoinColumn(name="medicationID", nullable = false)
    private Medication medicationID ;

    //Relacion con la entidad Patient
    @ManyToOne
    @JoinColumn(name="patientID", nullable = false)
    private Patient patientID ;

    //Fecha de incio 
    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    //Si esta confirmado 
    @Column(name = "isConfirmed", nullable = false)
    private Integer isConfirmed;

    //Duracion de la dosis en dias
    @Column(name = "durationDays", nullable = false)
    private Integer durationDays;

    // Constructor por defecto es necesario para JPA
    public ScheduleDose() {
    }

    // Constructor con parámetros
    public ScheduleDose(int doseID, Medication medicationID, Patient patientID, LocalDateTime startDate, Integer isConfirmed, Integer durationDays) {
        this.doseID = doseID;
        this.medicationID = medicationID;
        this.patientID = patientID;
        this.startDate = startDate;
        this.isConfirmed = isConfirmed;
        this.durationDays = durationDays;
    }

    // Getters y Setters
    public int getDoseID() {
        return doseID;
    }

    public void setDoseID(int doseID) {
        this.doseID = doseID;
    }

    public Medication getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(Medication medicationID) {
        this.medicationID = medicationID;
    }

    public Patient getPatientID() {
        return patientID;
    }

    public void setPatientID(Patient patientID) {
        this.patientID = patientID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Integer isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

}
