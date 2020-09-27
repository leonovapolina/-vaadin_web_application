package com.haulmont.backend;

import com.haulmont.ui.components.Viewable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;


public class Recipe implements Entity, Viewable<Recipe> {
    private final long id;
    private String description;
    private Date creationDate;
    private int validity;
    private RecipePriority priority;
    private Patient patient;
    private Doctor doctor;

    public Recipe(long id, String description, Date creationDate, int validity, Doctor doctor, Patient patient,
                  RecipePriority priority) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.validity = validity;
        this.doctor = doctor;
        this.patient = patient;
        this.priority = priority;
    }

    public static Recipe getEmpty() {
        return new Recipe(0, "", Date.valueOf(LocalDate.now()), 1, null, null,
                          RecipePriority.NORMAL);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = Date.valueOf(creationDate);
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public RecipePriority getPriority() {
        return priority;
    }

    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Recipe getCopy() {
        return new Recipe(id, description, creationDate, validity, doctor, patient, priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return validity == recipe.validity
                && Objects.equals(description, recipe.description)
                && Objects.equals(creationDate, recipe.creationDate)
                && priority == recipe.priority
                && patient.equals(recipe.patient)
                && doctor.equals(recipe.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, creationDate, validity, priority, patient, doctor);
    }

    @Override
    public String toString() {
        long patientId = patient == null ? -1 : patient.getId();
        long doctorId = patient == null ? -1 : patient.getId();
        return String.format("Id= %2d \tDoctorId= %2d \tPatientId= %2d \tCreationDate= %s \tValidity= %3d " +
                             "\tPriority= %-8s \tDescription= %s", id, doctorId, patientId, creationDate,
                             validity, priority, description);
    }
}