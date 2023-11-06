package com.mepetcu.main.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Qualifier;
import com.mepetcu.main.interfaces.ThymeleafElement;
import com.mepetcu.main.interfaces.ThymeleafRepository;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.ArrayList;


@Repository
@Qualifier("endpointsRepository")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResultRepository implements ThymeleafRepository {

    private final List<ThymeleafElement> elements;

    public ResultRepository() {
        elements = new ArrayList<>();
    }

    @Override
    public ThymeleafElement getNext() {
        if (elements.size() > 0) return elements.remove(0);
        return null;
    }

    @Override
    public ThymeleafElement getByIndex(int index) {
        if (elements.size() > index) return elements.remove(index);
        return null;
    }

    @Override
    public ThymeleafElement getByID(String ID) {
        ThymeleafElement element = elements.stream().filter(x -> x.getID().equals(ID)).findFirst().orElse(null);
        if (element != null) {
            elements.remove(element);
            return element;
        }
        return null;
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
