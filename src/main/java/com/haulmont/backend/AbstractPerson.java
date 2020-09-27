package com.haulmont.backend;

import com.haulmont.ui.components.Viewable;

import java.util.Objects;

public abstract class AbstractPerson<E> implements Viewable<E> {
    protected final long id;
    protected String name;
    protected String lastName;
    protected String patronymic;

    protected AbstractPerson(long id, String name, String lastName, String patronymic) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFullName() {
        return getLastName() + " " + getName() + " " + getPatronymic();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPerson)) {
            return false;
        }
        AbstractPerson<?> that = (AbstractPerson<?>) o;
        return Objects.equals(name, that.name)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(patronymic, that.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, patronymic);
    }
}
