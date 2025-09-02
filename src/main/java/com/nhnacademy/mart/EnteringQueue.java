package com.nhnacademy.mart;

import com.nhnacademy.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Customer 입장 대기열
 */
@Slf4j
public class EnteringQueue {

    private static final int DEFAULT_CAPACITY = 100;

    private final Queue<Customer> customerQueue;
    private final int capacity;

    public EnteringQueue() {
        this(DEFAULT_CAPACITY);
    }

    public EnteringQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity : %d".formatted(capacity));
        }
        this.capacity = capacity;
        customerQueue = new LinkedList<>();
    }

    /**
     * 입장대기큐의 사이즈가 capacity보다 많은 경우 추가하지 말고 대기해야한다.
     * @param customer 입장 대기열에 추가 할 손님
     */
    public synchronized void addCustomer(Customer customer) {
        while (customerQueue.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        customerQueue.add(customer);
        log.debug("customer add enteringQueue : {}", customer);

        notifyAll();
    }

    /**
     * 입장대기큐가 비어있다면 대기해야한다.
     * @return 입장대기큐에 있는 손님 한 명
     */
    public synchronized Customer getCustomer() {
        while (customerQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notifyAll();
        log.debug("customer get from enteringQueue");
        return customerQueue.poll();
    }

    public int getEnteringQueueSize () {
        return customerQueue.size();
    }

}
