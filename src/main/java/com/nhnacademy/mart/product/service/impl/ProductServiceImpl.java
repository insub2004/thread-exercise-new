package com.nhnacademy.mart.product.service.impl;

import com.nhnacademy.mart.product.domain.Product;
import com.nhnacademy.mart.product.exception.OutOfStackException;
import com.nhnacademy.mart.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.mart.product.exception.ProductNotFoundException;
import com.nhnacademy.mart.product.parser.ProductParser;
import com.nhnacademy.mart.product.repository.ProductRepository;
import com.nhnacademy.mart.product.service.ProductService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductParser productParser;

    public ProductServiceImpl(ProductRepository productRepository, ProductParser productParser) {
        if (Objects.isNull(productRepository) || Objects.isNull(productParser)) {
            throw new IllegalArgumentException("Invalid param");
        }
        this.productRepository = productRepository;
        this.productParser = productParser;

        init();
    }

    // csv 파일을 읽어서 MemoryRepository에 저장
    private void init() {
        List<Product> productList = productParser.parse();
        for (Product product : productList) {
            productRepository.save(product);
        }
    }

    @Override
    public Product getProduct(long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ProductNotFoundException("invalid id : %d".formatted(id));
        }
        return byId.get();
    }

    @Override
    public void saveProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            throw new ProductAlreadyExistsException("invalid product : %s".formatted(product));
        }
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("invalid id : %d".formatted(id));
        }
        productRepository.deleteById(id);
    }

    @Override
    public long getTotalCount() {
        return productRepository.productsTotalCount();
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("invalid id : %d".formatted(id));
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("invalid quantity : %d".formatted(quantity));
        }

        productRepository.updateQuantityById(id, quantity);
    }

    /**
     * 제품을 장바구니에 담을 때 사용하는 경우
     * @param id 제품의 식별자
     * @param quantity 장바구니에 담을 수량
     */
    @Override
    public void pickProduct(long id, int quantity) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("invalid id : %d".formatted(id));
        }
        if (quantity > productRepository.countQuantityById(id)) {
            throw new OutOfStackException("invalid quantity : %d".formatted(quantity));
        }

        Product savedProduct = getProduct(id);
        productRepository.updateQuantityById(id, savedProduct.getQuantity() - quantity);
    }

    /**
     * 장바구니에 있던 제품을 다시 되돌려 놓을 경우
     * @param id 제품의 식별자
     * @param quantity 되돌려 놓을 수량
     * @return
     */
    @Override
    public int returnProduct(long id, int quantity) {
        if (productRepository.existsById(id)) {
            throw new ProductNotFoundException("invalid id : %d".formatted(id));
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("invalid quantity : %d".formatted(quantity));
        }

        Product product = getProduct(id);
        int updatedQuantity = product.getQuantity() + quantity;
        productRepository.updateQuantityById(id, updatedQuantity);

        return updatedQuantity;
    }
}
