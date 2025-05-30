package com.example.recordatorio.DTO;

public class PatientDTO {
    private String name;
    private String email;
    private Boolean notificationPermission;


    public PatientDTO() {
    }

    public PatientDTO(String name, String email , Boolean notificationPermission) {
        this.name = name;
        this.email = email;
        this.notificationPermission = notificationPermission;
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

    public Boolean getNotificationPermission() {
        return notificationPermission;
    }
    
    public void setNotificationPermission(Boolean notificationPermission) {
        this.notificationPermission = notificationPermission;
    }
}
