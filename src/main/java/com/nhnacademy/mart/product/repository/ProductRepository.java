package com.nhnacademy.mart.product.repository;

import com.nhnacademy.mart.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    long productsTotalCount();

    int countQuantityById(long id);

    void updateQuantityById(long id, int quantity);
}
