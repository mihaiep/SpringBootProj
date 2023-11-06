package com.mepetcu.main.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mepetcu.main.elements.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(Integer id);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :name")
    List<Product> findProductByName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.category LIKE :category")
    List<Product> findProductByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.price LIKE concat(:price)")
    List<Product> findProductByPrice(String price);

    @Override
    <S extends Product> S save(S s);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id LIKE concat(:value)")
    Page<Product> findAndSortByID(String value, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE concat(:value)")
    Page<Product> findAndSortByName(String value, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category LIKE concat(:value)")
    Page<Product> findAndSortByCategory(String value, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price LIKE concat(:value)")
    Page<Product> findAndSortByPrice(String value, Pageable pageable);
}
