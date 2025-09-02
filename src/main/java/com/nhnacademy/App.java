package com.nhnacademy;

import com.nhnacademy.customer.generator.CustomerGenerator;
import com.nhnacademy.mart.EnteringQueue;

public class App {
    public static void main(String[] args) {
        EnteringQueue enteringQueue = new EnteringQueue(50);
        CustomerGenerator customerGenerator = new CustomerGenerator(enteringQueue);

        Thread enteringThread = new Thread(customerGenerator);
        enteringThread.setName("entering-thread");
        enteringThread.start();
    }
}
