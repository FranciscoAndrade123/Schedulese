package com.example.recordatorio.model;



import java.time.LocalDateTime;

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
    private LocalDateTime sendAt;

    @Column(name = "status", nullable = false)
    private int status;

    // Constructor por defecto es necesario para JPA
    public Reminder() {}

    // Constructor con parámetros
    public Reminder(int reminderID, ScheduleDose doseID, LocalDateTime sendAt, int status) {
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

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
