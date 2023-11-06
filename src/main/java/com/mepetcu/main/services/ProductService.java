package com.mepetcu.main.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.mepetcu.main.elements.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.mepetcu.main.elements.ProductFactory;
import org.springframework.data.domain.PageRequest;
import com.mepetcu.main.interfaces.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    private final ProductFactory productFactory = new ProductFactory();

    public Product generateRandomProduct() {
        return repository.save(productFactory.generateRandomProduct());
    }

    public Product insertProduct(String name, String category, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        return repository.save(product);
    }

    public Product findProductById(Integer id) {
        return repository.findProductById(id);
    }

    public List<Product> findProductByName(String name) {
        return repository.findProductByName(name);
    }

    public List<Product> findProductByCategory(String category) {
        return repository.findProductByCategory(category);
    }

    public List<Product> findProductByPrice(String price) {
        return repository.findProductByPrice(price);
    }

    public List<Product> deleteByIDs(String idsAsString) {
        List<Product> list = new ArrayList<>();
        int[] ids = Arrays.stream(idsAsString.split(",")).mapToInt(x -> Integer.parseInt(x.trim())).toArray();
        for (int id : ids) {
            Product product = repository.findProductById(id);
            if (product != null) {
                repository.delete(product);
                list.add(product);
            }
        }
        return list;
    }

    public List<Product> deleteByName(String name) {
        List<Product> list = repository.findProductByName(name);
        for (Product p : list)
            repository.delete(p);
        return list;
    }

    public Page<Product> getDefaultPage(Integer pageNumber) {
        return repository.findAll(PageRequest.of(pageNumber, 10, Sort.by("id").ascending()));
    }

    public Page<Product> getPageSorted(Integer pageNumber, String sortAttribute, boolean isAscending) {
        Sort sort = Sort.by(sortAttribute);
        if (isAscending) sort = sort.ascending();
        else sort = sort.descending();
        return repository.findAll(PageRequest.of(pageNumber, 10, sort));
    }

    public Page<Product> getPageFilteredAndSorted(Integer pageNumber, String filterValue, String filterAttribute, String sortAttribute, boolean isAscending) {
        Sort sort = Sort.by(sortAttribute.toLowerCase());
        if (isAscending) sort = sort.ascending();
        else sort = sort.descending();
        if (filterAttribute.equalsIgnoreCase("name")) {
            return repository.findAndSortByName(filterValue, PageRequest.of(pageNumber, 10, sort));
        } else if (filterAttribute.equalsIgnoreCase("category")) {
            return repository.findAndSortByCategory(filterValue, PageRequest.of(pageNumber, 10, sort));
        } else if (filterAttribute.equalsIgnoreCase("price")) {
            return repository.findAndSortByPrice(filterValue, PageRequest.of(pageNumber, 10, sort));
        } else {
            return repository.findAndSortByID(filterValue, PageRequest.of(pageNumber, 10, sort));
        }
    }

}
