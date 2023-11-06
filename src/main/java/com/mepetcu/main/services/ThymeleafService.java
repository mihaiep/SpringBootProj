package com.mepetcu.main.services;

import com.mepetcu.main.interfaces.ThymeleafElement;
import com.mepetcu.main.interfaces.ThymeleafRepository;


public class ThymeleafService {

    private final ThymeleafRepository repository;

    public ThymeleafService(ThymeleafRepository repository) {
        this.repository = repository;
    }

    public ThymeleafElement getNext() {
        return repository.getNext();
    }

    public ThymeleafElement getByIndex(int index) {
        return repository.getByIndex(index);
    }

    public ThymeleafElement getByID(String ID) {
        return repository.getByID(ID);
    }

    public void add(ThymeleafElement element) { repository.add(element); }

    public void remove(ThymeleafElement element) {
        repository.remove(element);
    }

    public void removeByIndex(int index) {
        repository.removeByIndex(index);
    }

    public void removeByID(String ID) {
        repository.removeByID(ID);
    }

    public int size() {
        return repository.size();
    }

}
