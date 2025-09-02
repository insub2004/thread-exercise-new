package com.nhnacademy.mart.product.domain;

import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class Product {
    private final long id;
    private final String itemName;
    private final String maker;
    private final String specification;
    private final String unit;
    private final int price;
    private int quantity;

    public Product(final long id, final String itemName, String maker, String specification, String unit, int price, int quantity) {
        if(parameterValidCheck(id, itemName, maker, specification, unit, price, quantity)) {
            throw new IllegalArgumentException("product invalid param!");
        }
        this.id = id;
        this.itemName = itemName;
        this.maker = maker;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        this.quantity = quantity;
    }

    private boolean parameterValidCheck(long id, String itemName, String maker, String specification, String unit, int price, int quantity) {
        if (id < 0 || price < 0 || quantity <0) {
            return true;
        }
        if (Strings.isNullOrEmpty(itemName)) {
            return true;
        }
        if (Strings.isNullOrEmpty(maker)) {
            return true;
        }
        if (Strings.isNullOrEmpty(specification)) {
            return true;
        }
        if (Strings.isNullOrEmpty(unit)) {
            return true;
        }
        return false;
    }
}
