package com.nhnacademy.customer.generator;

import com.nhnacademy.mart.EnteringQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class CustomerGeneratorTest {

    CustomerGenerator customerGenerator;
    EnteringQueue enteringQueue;

    @BeforeEach
    void setUp() {
        enteringQueue = new EnteringQueue(5);
        customerGenerator = new CustomerGenerator(enteringQueue);
    }

    @Test
    @DisplayName("enteringQueue is null")
    void constructorTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomerGenerator(null));
    }

    @Test
    @DisplayName("특정 시간동안 random customer 5명 이상 entering queue에 들어가는지 확인")
    void generatorTest() {
        Thread generator = new Thread(customerGenerator);
        generator.start();

        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertEquals(5, enteringQueue.getEnteringQueueSize());
                    generator.interrupt();
                });


        assertAll(
                () -> {
                    assertEquals(Thread.State.TERMINATED, generator.getState());
                },
                () -> {
                    assertEquals(5, enteringQueue.getEnteringQueueSize());
                }
        );
    }
}