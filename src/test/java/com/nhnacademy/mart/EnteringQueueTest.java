package com.nhnacademy.mart;

import com.nhnacademy.customer.domain.Customer;
import com.nhnacademy.mart.entering.EnteringQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EnteringQueueTest {

    EnteringQueue enteringQueue;

    @BeforeEach
    void setUp() {
        enteringQueue = new EnteringQueue();

        // Customer{id=1, name="customer1", money=10000} ~ Customer{id=99, name="customer99", money=10000} 생성 후 대기열에 추가
        for (int i = 1; i < 100; i++) {
            enteringQueue.addCustomer(new Customer(i, "customer"+i, 10000));
        }
    }

    @Test
    @DisplayName("default capacity is 100")
    void constructorTest_InitCapacity() throws Exception {
        Try<Object> capacity = ReflectionUtils.tryToReadFieldValue(EnteringQueue.class, "capacity", enteringQueue);
        assertEquals(100, (int)capacity.get());
    }

    @Test
    @DisplayName("addCustomer test")
    void addCustomer() {
        assertEquals(99, enteringQueue.getEnteringQueueSize());

        enteringQueue.addCustomer(new Customer(100, "customer100", 10000));

        assertEquals(100, enteringQueue.getEnteringQueueSize());
    }

    @Test
    @DisplayName("getCustomer test")
    void getCustomer() {
        assertEquals(99, enteringQueue.getEnteringQueueSize());

        Customer customer = enteringQueue.getCustomer();

        assertEquals(98, enteringQueue.getEnteringQueueSize());
        assertEquals(new Customer(1L, "customer1", 10000), customer);
    }

    @Test
    @DisplayName("blocking test")
    void addBlockingTest() {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                enteringQueue.addCustomer(new Customer(100, "customer100", 10000));
                log.debug("현재 입장대기열의 큐가 100명으로 꽉차 있어서 2초간 대기");
                enteringQueue.addCustomer(new Customer(101, "customer101", 10000));
                log.debug("100명째 customer 추가 성공");
            }
        });
        producer.start();

        Thread consumer = new Thread(() -> {
            // org.awaitility.Awaitility
            await()
                    .pollDelay(2, TimeUnit.SECONDS)
                    .atMost(3, TimeUnit.SECONDS)
                    .untilAsserted(() -> {
                        // 입장대기큐를 비워주기 전까지는 producer는 대기상태
                        assertEquals(Thread.State.WAITING, producer.getState());
                        Customer customer = enteringQueue.getCustomer();
                        assertEquals(new Customer(1L, "customer1", 10000), customer);
                    });
        });
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(100, enteringQueue.getEnteringQueueSize());
    }

}