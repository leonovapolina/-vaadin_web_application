package com.haulmont.ui.components;

import com.haulmont.backend.Entity;

public interface Viewable<E> extends Entity {
    E getCopy();
}
