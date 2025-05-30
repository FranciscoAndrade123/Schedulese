package com.example.recordatorio.DTO;


import java.time.LocalDateTime;

public class ReminderDTO {

    private int doseID;
    private LocalDateTime sendAt;
    private int status;

    // Constructor por defecto
    public ReminderDTO() {}

    // Constructor con parámetros
    public ReminderDTO(int doseID, LocalDateTime sendAt, int status) {
        this.doseID = doseID;
        this.sendAt = sendAt;
        this.status = status;
    }

    // Getters y Setters
    public int getDoseID() {
        return doseID;
    }

    public void setDoseID(int doseID) {
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
