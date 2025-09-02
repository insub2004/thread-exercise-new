package com.nhnacademy.customer.generator;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.nhnacademy.customer.domain.Customer;
import com.nhnacademy.mart.entering.EnteringQueue;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 랜덤 customer 생성, enteringQueue에 등록
 */
@Slf4j
public class CustomerGenerator implements Runnable {

    private EnteringQueue enteringQueue;
    private AtomicInteger atomicCustomerId;
    private final static int DEFAULT_MONEY = 10_0000;

    public CustomerGenerator(EnteringQueue enteringQueue) {
        if (Objects.isNull(enteringQueue)) {
            throw new IllegalArgumentException("Invalid enteringQueue");
        }
        this.enteringQueue = enteringQueue;
        atomicCustomerId = new AtomicInteger(0);
    }

    /**
     * 1초 간격으로 customer 생성 후 enteringQueue에 등록
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Customer customer = generateRandomCustomer();
                enteringQueue.addCustomer(customer);
                log.debug("generate-customer:{}",customer);

                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                log.error("interrupt!!");
                Thread.currentThread().interrupt(); // interrupt 상태 초기화
            }
        }
    }

    private Customer generateRandomCustomer() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        long id = atomicCustomerId.incrementAndGet();
        String name = person.getFullName();
        return new Customer(id, name, DEFAULT_MONEY);
    }
}
