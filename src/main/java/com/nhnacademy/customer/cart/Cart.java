package com.nhnacademy.customer.cart;

import com.nhnacademy.mart.product.exception.ProductAlreadyExistsException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CartItem> cartItems;

    public Cart() {
        // TODO 2
        // 장바구니를 초기화 할 때, List<CartItem>을 동기화 처리 해보자.
        // https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html#synchronizedList(java.util.List)
        cartItems = Collections.synchronizedList(new ArrayList<>());
    }

    public void tryAddItem(CartItem cartItem) {
        // 장바구니에 cartItem이 존재하면 예외 발생
        if (cartItems.contains(cartItem)) {
            throw new ProductAlreadyExistsException("product Already Exists!");
        }

        cartItems.add(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clear() {
        cartItems.clear();
    }

}
