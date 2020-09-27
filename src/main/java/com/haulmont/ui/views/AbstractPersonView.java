package com.haulmont.ui.views;

import com.haulmont.backend.AbstractPerson;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.ui.components.Action;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public abstract class AbstractPersonView<E extends AbstractPerson<E>> extends AbstractView<E> {
    protected final TextField nameField = new TextField("Имя");
    protected final TextField lastNameField = new TextField("Фамилия");
    protected final TextField patronymicField = new TextField("Отчество");
    protected final TextField personField = getPersonField();

    public AbstractPersonView(String labelText, AbstractEntityDAO<E> entityDao) {
        super(labelText, entityDao);
        addLocalComponents(this);
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    protected abstract void addGridColumn(Grid<E> grid);

    protected abstract E getEmptyPerson();

    protected abstract TextField getPersonField();

    protected abstract void bindPersonField(TextField personField);

    @Override
    protected Grid<E> createGrid() {
        Grid<E> grid = new Grid<>();
        grid.addColumn(E::getName).setCaption("Имя");
        grid.addColumn(E::getLastName).setCaption("Фамилия");
        grid.addColumn(E::getPatronymic).setCaption("Отчество");
        addGridColumn(grid);
        return grid;
    }

    @Override
    protected FormLayout createInputFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();

        E entity;
        if (action == Action.ADD) {
            entity = getEmptyPerson();
            binder.setBean(entity);
        }

        bindFieldOfNaming(nameField, E::getName, E::setName);
        bindFieldOfNaming(lastNameField, E::getLastName, E::setLastName);
        bindFieldOfNaming(patronymicField, E::getPatronymic, E::setPatronymic);
        bindPersonField(personField);

        if (action == Action.ADD) {
            nameField.clear();
            lastNameField.clear();
            patronymicField.clear();
            personField.clear();
        }

        formLayout.addComponents(nameField, lastNameField, patronymicField, personField);
        return formLayout;
    }

    @Override
    public boolean fieldNotValid() {
        return !Validation.namedIsValid(nameField.getValue())
                || !Validation.namedIsValid(lastNameField.getValue())
                || !Validation.namedIsValid(patronymicField.getValue());
    }

    protected void bindFieldOfNaming(TextField field, ValueProvider<E, String> getter, Setter<E, String> setter) {
        binder.forField(field).withValidator(Validation::namedIsValid, Message.nameValidationError())
              .bind(getter, setter);
    }
}
    
