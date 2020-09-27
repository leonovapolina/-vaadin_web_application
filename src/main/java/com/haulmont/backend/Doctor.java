package com.haulmont.backend;

import java.util.Objects;

public class Doctor extends AbstractPerson<Doctor> {
    private String specialization;

    public Doctor(long id, String name, String lastName, String patronymic, String specialization) {
        super(id, name, lastName, patronymic);
        this.specialization = specialization;
    }

    public static Doctor getEmpty() {
        return new Doctor(0, "", "", "", "");
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Doctor getCopy() {
        return new Doctor(id, name, lastName, patronymic, specialization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        return specialization.equals(doctor.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization);
    }

    @Override
    public String toString() {
        return String.format("Id= %2d  \tName= %-12s \tLastName= %-12s \tPatronymic= %-12s \tSpecialization= %-15s",
                             id, name, lastName, patronymic, specialization);
    }
}
