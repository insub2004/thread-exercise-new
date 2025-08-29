package com.nhnacademy.serialize;

import com.nhnacademy.customer.cart.Cart;
import com.nhnacademy.customer.cart.CartItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteMain {
    public static void main(String[] args) {
        Person person = new Person("홍길동", 123, 1_000_000_000, "test@test.com");
        person.cart = new Cart();
        person.cartItem = new CartItem(12L, 123);
        String fileName = "person.txt";

        try (
                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
        ) {
            out.writeObject(person);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
