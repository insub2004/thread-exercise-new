package com.nhnacademy.mart.product.repository.impl;

import com.nhnacademy.mart.product.domain.Product;
import com.nhnacademy.mart.product.repository.ProductRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    @Override
    public void save(Product product) {

        //TODO#6-4-1 product 저장
        productMap.put(product.getId(), product);
    }
    @Override
    public Optional<Product> findById(long id) {
        //TODO#6-4-2 id에 해당되는 product 조회
        Product product = productMap.get(id);
        return Objects.isNull(product) ? Optional.empty() : Optional.of(product);
    }

    @Override
    public void deleteById(long id) {
        //TODO#6-4-3 id에 해당하는 product 삭제
        productMap.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        //TODO#6-4-4 id에 해당하는 product 존재여부를 체크해서 반환 합니다.
        return productMap.containsKey(id);
    }

    @Override
    public long productsTotalCount() {
        //TODO#6-4-5 전체 product 수 반환
        return productMap.size();
    }

    @Override
    public int countQuantityById(long id) {
        //TODO#6-4-6 id에 해당되는 product의 수량 반환(즉 제고 확인)
        Product product = productMap.get(id);
        return product.getQuantity();
    }

    @Override
    public void updateQuantityById(long id, int quantity) {
        //TODO#6-4-7 id에 해당되는 product의 수량 변경
        Product product = productMap.get(id);
        product.setQuantity(quantity);
    }
}
