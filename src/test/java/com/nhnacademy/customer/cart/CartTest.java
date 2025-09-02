package com.nhnacademy.customer.cart;

import com.nhnacademy.mart.product.exception.ProductAlreadyExistsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.tryAddItem(new CartItem(1L, 1));
    }

    @Test
    @DisplayName("cart가 Serializable implement하는지 체크")
    void implementCheck() {
        assertInstanceOf(Serializable.class, cart);
    }

    @Test
    @DisplayName("cart에 제대로 cartItem이 추가되는 경우")
    @ParameterizedTest
    void addCartItem() {
        cart.tryAddItem(new CartItem(2L, 2));
        cart.tryAddItem(new CartItem(3L, 3));

        assertEquals(3, cart.getCartItems().size());
    }

    // 이미 존재하는 제품 추가하려고 할 때
    @Test
    @DisplayName("아마 존재하는 cartItem을 추가하는 경우")
    void addDuplicateCartItem() {
        assertThrows(ProductAlreadyExistsException.class, () -> cart.tryAddItem(new CartItem(1L, 1)));
    }

    // 장바구니가 제대로 비워 지는지
    @Test
    @DisplayName("장바구니가 비워지는지 확인")
    void clearCart() {
        cart.clear();
        assertTrue(cart.getCartItems().size() == 0);
    }
}