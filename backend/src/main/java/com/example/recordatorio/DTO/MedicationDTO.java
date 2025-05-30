package com.example.recordatorio.DTO;

public class MedicationDTO {
    private String name;
    private String dosage;
    private int frequencyHours;

    // Constructor por defecto es necesario para la serialización/deserialización
    public MedicationDTO() {
    }

    // Constructor con parámetros
    public MedicationDTO(String name, String dosage, int frequencyHours) {
        this.name = name;
        this.dosage = dosage;
        this.frequencyHours = frequencyHours;
    }

    // Getters y Setters
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

    public int getfrequencyHours() {
        return frequencyHours;
    }

    public void setfrequencyHours(int frequencyHours) {
        this.frequencyHours = frequencyHours;
    }
    
}
