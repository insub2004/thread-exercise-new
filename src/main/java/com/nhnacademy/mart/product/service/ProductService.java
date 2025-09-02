package com.nhnacademy.mart.product.service;

import com.nhnacademy.mart.product.domain.Product;

public interface ProductService {
    //product 조회
    Product getProduct(long id);
    //product 등록 후 product id를 응답 한다.
    void saveProduct(Product product);
    //product 삭제
    void deleteProduct(long id);

    //전체 상품의 개수를 구합니다.
    long getTotalCount();

    //제품의 수량을 수정 한다.
    void updateQuantity(long id, int quantity);

    //제품을 들어서 카트에 담는다.
    void pickProduct(long id, int quantity);

    //제품을 반납 합니다. 반납된 수량을 더한 수량 반환
    int returnProduct(long id, int quantity);

}
