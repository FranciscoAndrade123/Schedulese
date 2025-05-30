package com.example.recordatorio.model;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity (name="Reminder")
public class Reminder {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reminderID") 
    private int reminderID ;

    //Relacion con la entidad ScheduleDose
    @ManyToOne
    @JoinColumn(name="doseID", nullable = false)
    private ScheduleDose doseID ;

    
    @Column(name = "sendAt", nullable = false)
    private Timestamp sendAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    // Constructor por defecto es necesario para JPA
    public Reminder() {}

    // Constructor con parámetros
    public Reminder(int reminderID, ScheduleDose doseID, Timestamp sendAt, Boolean status) {
        this.reminderID = reminderID;
        this.doseID = doseID;
        this.sendAt = sendAt;
        this.status = status;
    }

    // Getters y Setters

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public ScheduleDose getDoseID() {
        return doseID;
    }

    public void setDoseID(ScheduleDose doseID) {
        this.doseID = doseID;
    }

    public Timestamp getSendAt() {
        return sendAt;
    }

    public void setSendAt(Timestamp sendAt) {
        this.sendAt = sendAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



}
