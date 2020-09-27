package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.dao.DoctorDao;
import com.haulmont.ui.components.Validation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.Map;

public class DoctorsView extends AbstractPersonView<Doctor> {
    private static final String COLUMN_QUANTITY_ID = "STATISTICS";
    private Button buttonStatistics;

    public DoctorsView() {
        super("Врачи", new DoctorDao());
    }

    @Override
    protected void localEnter() {
        if (buttonStatistics.getData() != null) {
            grid.removeColumn(COLUMN_QUANTITY_ID);
            addColumnQuantity();
        }
    }

    @Override
    protected void addLocalComponents(VerticalLayout layout) {
        buttonStatistics = new Button("Показать статистику");
        buttonStatistics.setWidth("180px");

        buttonStatistics.addClickListener(event -> {
            if (buttonStatistics.getData() == null) {
                addColumnQuantity();
                buttonStatistics.setData(true);
                buttonStatistics.setCaption("Скрыть статистику");
                grid.getDataProvider().refreshAll();
                return;
            }
            grid.removeColumn(COLUMN_QUANTITY_ID);
            buttonStatistics.setData(null);
            buttonStatistics.setCaption("Показать статистику");
        });
        layout.addComponent(buttonStatistics);
    }

    @Override
    public boolean fieldNotValid() {
        return (super.fieldNotValid() || !Validation.namedIsValid(personField.getValue()));
    }

    @Override
    protected void addGridColumn(Grid<Doctor> grid) {
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация");
    }

    @Override
    protected Doctor getEmptyPerson() {
        return Doctor.getEmpty();
    }

    @Override
    protected TextField getPersonField() {
        return new TextField("Специализация");
    }

    @Override
    protected void bindPersonField(TextField specializationField) {
        bindFieldOfNaming(specializationField, Doctor::getSpecialization, Doctor::setSpecialization);
    }

    private void addColumnQuantity() {
        Map<Long, Integer> mapQuantity = ((DoctorDao) entityDao).getQuantityRecipes();
        grid.addColumn(doctor -> {
            Integer quantity = mapQuantity.get(doctor.getId());
            return quantity == null ? 0 : quantity;
        }).setId(COLUMN_QUANTITY_ID).setCaption("Количество рецептов");
    }
}
