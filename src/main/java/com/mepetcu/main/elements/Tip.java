package com.mepetcu.main.elements;

import com.mepetcu.main.interfaces.ThymeleafElement;


public class Tip implements ThymeleafElement {

    private final String ID;
    private final String value;

    public Tip(String value) {
        ID = "tipID";
        this.value = value;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Object getWebObject() {
        return null;
    }

}
