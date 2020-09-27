package com.haulmont.ui.components;

public abstract class Message {

    public static String nameValidationError() {
        return "Поле может содержать только буквы, длина от 2 до 30 символов.";
    }

    public static String phoneValidationError() {
        return "Номер телефона может содержать только цифры или начинаться с '+7', длина от 6 до 15.";
    }

    public static String validityValidationError() {
        return "Срок действия может быть от 1 до 365 дней.";
    }

    public static String descriptionValidationError() {
        return "Описание должно содержать больше 10 символов.";
    }

    public static String notEmptyError(String type) {
        return "Поле \"" + type + "\" не может быть пустым.";
    }
}
