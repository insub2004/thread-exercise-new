package com.nhnacademy.customer.domain;

import com.nhnacademy.customer.exception.InsufficientFundsException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Slf4j
public class Customer {

    private final long id;
    private final String name;
    private int money;

    public Customer(final long id, final String name, int money) {
        if (id < 1 || Objects.isNull(name) || name.isEmpty() || money < 0) {
            throw new IllegalArgumentException("Invalid Customer param");
        }

        this.id = id;
        this.name = name;
        this.money = money;
    }

    public void pay (int amount) throws InsufficientFundsException {
        if (amount < 0) {
            throw new IllegalArgumentException("amount under zero");
        }
        if (amount > money) {
            throw new InsufficientFundsException("amount is more than present money");
        }

        money = money - amount;

        log.debug("customer : {}, pay amount : {}, present money : {}", this, amount, money);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && money == customer.money && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, money);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
