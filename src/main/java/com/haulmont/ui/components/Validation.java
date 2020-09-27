package com.haulmont.ui.components;

public abstract class Validation {

    public static boolean namedIsValid(String text) {
        String regEx = "^[а-яА-ЯёЁa-zA-Z]{2,30}$";
        return text.matches(regEx);
    }

    public static boolean phoneIsValid(String text) {
        String regEX = "^([0-9]|\\+7)[0-9]{5,15}$";
        return text.matches(regEX);
    }

    public static boolean validityIsValid(String text) {
        int validity;
        try {
            validity = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return ((validity > 0) && (validity <= 365));
    }

    public static boolean descriptionIsValid(String text) {
        return (text.length() > 10);
    }

    public static boolean notNull(Object object) {
        return (object != null);
    }
}