package com.haulmont.ui.views;

import com.haulmont.backend.Patient;
import com.haulmont.backend.dao.PatientDao;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PatientsView extends AbstractPersonView<Patient> {

    public PatientsView() {
        super("Пациенты", new PatientDao());
    }

    @Override
    protected void localEnter() {
    }

    @Override
    protected void addGridColumn(Grid<Patient> grid) {
        grid.addColumn(Patient::getPhone).setCaption("Телефон");
    }

    @Override
    protected Patient getEmptyPerson() {
        return Patient.getEmpty();
    }

    @Override
    protected TextField getPersonField() {
        return new TextField("Телефон");
    }

    @Override
    protected void bindPersonField(TextField phoneField) {
        binder.forField(phoneField).withValidator(Validation::phoneIsValid, Message.phoneValidationError())
              .bind(Patient::getPhone, Patient::setPhone);
    }

    @Override
    public boolean fieldNotValid() {
        return (super.fieldNotValid() || !Validation.phoneIsValid(personField.getValue()));
    }

    @Override
    protected void addLocalComponents(VerticalLayout layout) {
    }
}
