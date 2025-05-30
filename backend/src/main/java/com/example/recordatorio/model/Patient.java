package com.example.recordatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity (name="patient")

public class Patient {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patientID") 
    private int patientID ;

    
    @Column(name="name",length = 50,nullable = false)
    private String name;

    @Column(name="email",length = 70,nullable = false)
    private String email;

    @Column(name="notificationPermission",nullable = false,  columnDefinition = "boolean default true ")
    private boolean notificationPermission ;

    //El constructor por defecto es necesario para JPA
    public Patient() {
    }

    //Constructor con parámetros
    public Patient(int patientID, String name, String email, boolean notificationPermission) {
        this.name = name;
        this.email = email;
        this.notificationPermission = notificationPermission;
        this.patientID = patientID;
    }

    // Getters y Setters
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getNotificationPermission() {
        return notificationPermission;
    }

    public void setNotificationPermission(boolean notificationPermission) {
        this.notificationPermission = notificationPermission;
    }

}
