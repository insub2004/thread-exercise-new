package com.nhnacademy.mart.product.util;

import org.apache.commons.math3.stat.inference.OneWayAnova;

import java.util.concurrent.atomic.AtomicLong;

public class ProductIdGenerator {

    private final static AtomicLong atomicLong = new AtomicLong(0L);

    public static long getNewProductId() {
        return atomicLong.incrementAndGet();
    }
}
