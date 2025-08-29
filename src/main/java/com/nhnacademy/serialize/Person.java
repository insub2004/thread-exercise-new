package com.nhnacademy.serialize;

import com.nhnacademy.customer.cart.Cart;
import com.nhnacademy.customer.cart.CartItem;

import java.io.*;
import java.util.Arrays;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private long money;
    transient private String email;
    static private boolean flag = false;
    private String suidTest;
    private int[] test;
    public Cart cart;
    public CartItem cartItem;

    public Person(String name, int age, long money, String email) {
        this.name = name;
        this.age = age;
        this.money = money;
        this.email = email;
    }

    public void testMethod() {
        // versionUUID check test
    }

//    // 직렬화 재정의
//    @Serial
//    private void writeObject(ObjectOutputStream outputStream) throws IOException {
//        outputStream.writeObject(name);
//        outputStream.writeInt(age);
//    }
//
//    // 역직렬화 재정의
//    @Serial
//    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
//        this.name = (String)inputStream.readObject();
//        this.age = inputStream.readInt();
//    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", email='" + email + '\'' +
                ", suidTest='" + suidTest + '\'' +
                ", test=" + Arrays.toString(test) +
                ", cart=" + cart +
                ", cartItem=" + cartItem +
                '}';
    }
}
