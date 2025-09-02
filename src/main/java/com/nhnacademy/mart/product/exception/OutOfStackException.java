package com.nhnacademy.mart.product.exception;

/**
 * 제품 수량보다 장바구니에 담으려는 수량이 더 많을 경우
 */
public class OutOfStackException extends RuntimeException {
    public OutOfStackException(String message) {
        super(message);
    }
}
