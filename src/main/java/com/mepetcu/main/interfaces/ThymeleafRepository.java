package com.mepetcu.main.interfaces;


public interface ThymeleafRepository {

    ThymeleafElement getNext();

    ThymeleafElement getByIndex(int index);

    ThymeleafElement getByID(String ID);

    void add(ThymeleafElement element);

    void remove(ThymeleafElement element);

    void removeByIndex(int index);

    void removeByID(String ID);

    int size();

}
