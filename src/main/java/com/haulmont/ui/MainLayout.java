package com.haulmont.ui;

import com.haulmont.ui.views.DoctorsView;
import com.haulmont.ui.views.PatientsView;
import com.haulmont.ui.views.RecipesView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.*;

public class MainLayout extends VerticalLayout {
    private final Panel contentWrapper;
    private final Navigator navigator;

    public MainLayout() {
        addComponent(createNavigationLayout());

        contentWrapper = new Panel();
        contentWrapper.setSizeFull();
        addComponent(contentWrapper);
        setExpandRatio(contentWrapper, 1);

        navigator = new Navigator(UI.getCurrent(), contentWrapper);
        navigator.addView("PatientsView", new PatientsView());
        navigator.addView("DoctorsView", new DoctorsView());
        navigator.addView("RecipesView", new RecipesView());

        navigateTo("RecipesView");
    }

    private Layout createNavigationLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        Button buttonPatients = new Button("Пациенты");
        Button buttonDoctors = new Button("Врачи");
        Button buttonRecipes = new Button("Рецепты");
        buttonPatients.setWidth("180px");
        buttonDoctors.setWidth("180px");
        buttonRecipes.setWidth("180px");
        buttonPatients.addClickListener(clickEvent -> navigateTo("PatientsView"));
        buttonDoctors.addClickListener(clickEvent -> navigateTo("DoctorsView"));
        buttonRecipes.addClickListener(clickEvent -> navigateTo("RecipesView"));

        layout.addComponent(buttonPatients);
        layout.addComponent(buttonDoctors);
        layout.addComponent(buttonRecipes);

        return layout;
    }

    private void navigateTo(String viewId) {
        navigator.navigateTo(viewId);
    }
}
