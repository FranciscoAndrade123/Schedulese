package com.example.recordatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity (name="medication")


public class Medication {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medicationID") 
    private int medicationID ;
    
    @Column(name="name",length = 200,nullable = false)
    private String name;

    @Column(name="dosage",length = 10,nullable = false)
    private String dosage;

    @Column(name="frequencyHours",length = 10,nullable = false)
    private int frequencyHours;

    //Constructor por defecto es necesario para JPA
    public Medication() {
    }

    //Constructor con parámetros
    public Medication(int medicationID, String name, String dosage, int frequencyHours) {
        this.name = name;
        this.dosage = dosage;
        this.frequencyHours = frequencyHours;
        this.medicationID = medicationID;
    }

    // Getters y Setters
    public int getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getFrequencyHours() {
        return frequencyHours;
    }

    public void setFrequencyHours(int frequencyHours) {
        this.frequencyHours = frequencyHours;
    }


}
