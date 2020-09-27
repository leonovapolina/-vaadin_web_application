package com.haulmont.ui.components;

import com.haulmont.ui.views.AbstractView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class InputFormWindow<E extends Viewable<E>> extends Window {
    private HorizontalLayout layout = new HorizontalLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Button acceptButton = new Button("ОК");
    private Button cancelButton = new Button("Отменить");

    public InputFormWindow(AbstractView<E> abstractView, FormLayout formLayout, E entity, Action action) {
        setResizable(false);
        setModal(true);
        setClosable(false);
        formLayout.setMargin(true);
        buttonLayout.setMargin(new MarginInfo(true, false, false, false));

        E selectedEntity = entity.getCopy();

        acceptButton.setClickShortcut(KeyCode.ENTER);
        acceptButton.addClickListener(event -> {
            if (abstractView.fieldNotValid()) {
                Notification.show("Заполните, пожалуйста, все поля");
                return;
            }
            switch (action) {
                case ADD:
                    if (!abstractView.doAdd(entity)) {
                        return;
                    }
                    break;
                case UPDATE:
                    if (!abstractView.doUpdate(selectedEntity)) {
                        return;
                    }
                    break;
            }
            close();
        });

        cancelButton.addClickListener(event -> close());

        buttonLayout.addComponents(acceptButton, cancelButton);
        formLayout.addComponent(buttonLayout);
        layout.addComponent(formLayout);
        setContent(layout);
    }
}
