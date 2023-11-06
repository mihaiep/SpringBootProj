package com.mepetcu.main.elements;

import com.mepetcu.main.interfaces.ThymeleafElement;


public class Result implements ThymeleafElement {

    private final String ID;
    private final String value;
    private Object object = null;

    public Result(String ID, String value) {
        this.ID = ID;
        this.value = value;
    }

    public static Result getInstance(String id, Object object) {
        Result result = new Result(id, "dummy");
        result.setObject(object);
        return result;
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
        return object;
    }

    private void setObject(Object object) {
        this.object = object;
    }

}
