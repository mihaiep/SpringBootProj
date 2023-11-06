package com.mepetcu.main.repositories;

import com.mepetcu.main.interfaces.ThymeleafElement;
import com.mepetcu.main.interfaces.ThymeleafRepository;

import java.util.List;
import java.util.ArrayList;


public class InMemoryTipsRepository implements ThymeleafRepository {

    private int index;
    List<ThymeleafElement> elements;

    public InMemoryTipsRepository() {
        index = 0;
        elements = new ArrayList<>();
    }

    @Override
    public ThymeleafElement getNext() {
        if (this.index >= elements.size()) this.index = 0;
        return elements.get(this.index++);
    }

    @Override
    public ThymeleafElement getByIndex(int index) {
        return elements.get(index);
    }

    @Override
    public ThymeleafElement getByID(String ID) {
        return elements.stream().filter(x -> x.getID().equals(ID)).findFirst().orElse(null);
    }

    @Override
    public void add(ThymeleafElement element) {
        elements.add(element);
    }

    @Override
    public void remove(ThymeleafElement element) {
        throw new RuntimeException("Method 'remove' is not supported by this class " + this.getClass().getName());
    }

    @Override
    public void removeByIndex(int index) {
        throw new RuntimeException("Method 'removeByIndex' is not supported by this class " + this.getClass().getName());
    }

    @Override
    public void removeByID(String ID) {
        throw new RuntimeException("Method 'removeById' is not supported by this class " + this.getClass().getName());
    }

    @Override
    public int size() {
        return elements.size();
    }

}
