/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

        
public enum MedicalTitle {
    DOCTOR("Dr."),             // Osnovna titula doktora medicine
    SPECIALIST_DOCTOR("Dr. Spec. Med."), // Doktor specijalista
    PROFESSOR("Prof. Dr."),    // Profesor doktor
    ACADEMICIAN("Akad. Dr."),  // Akademik doktor
    PRIMARIUS("Prim. Dr.");    // Primarius doktor

    private final String displayName;

    MedicalTitle(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

