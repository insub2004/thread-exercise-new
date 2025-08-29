package com.nhnacademy.customer.cart;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

@Slf4j
public record CartItem(long productId, int quantity) implements Serializable {

    public CartItem {
        if (productId < 0 || quantity < 0) {
            throw new IllegalArgumentException("Invalid CartItem param!");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (Objects.isNull(o) || o.getClass() != this.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;

        // 이부분은 실제 서비스용 요구사항에서는 다르게 해야 될 것 같다.
        return productId == cartItem.productId() && quantity == cartItem.quantity;
    }

}
