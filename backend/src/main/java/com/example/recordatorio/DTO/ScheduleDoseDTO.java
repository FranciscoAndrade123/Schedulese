package com.example.recordatorio.DTO;

import java.time.LocalDateTime;

public class ScheduleDoseDTO {
    private  int medicationID;
    private  int patientID;
    private LocalDateTime startDate;
    private int isConfirmed ;   // 0=pending, 1=confirmed, 2=not taken
    private int durationDays;

    public ScheduleDoseDTO() {
    }

    public ScheduleDoseDTO(int medicationID, int patientID, LocalDateTime startDate, int isConfirmed, int durationDays) {
        this.medicationID = medicationID;
        this.patientID = patientID;
        this.startDate = startDate;
        this.isConfirmed = isConfirmed;
        this.durationDays = durationDays;
    }

    public int getMedicationID() {
        return medicationID;
    }
    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(int isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
 }
 
  
}
