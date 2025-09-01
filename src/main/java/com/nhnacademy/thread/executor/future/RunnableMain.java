package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RunnableMain {

    public static void main(String[] args) {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = task.value;
        log.debug("result value = {}", result);
    }

    static class MyRunnable implements Runnable {

        int value;

        @Override
        public void run() {
            log.debug("Runnable 시작");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            value = new Random().nextInt(10);
            log.debug("create value : {}", value);
            log.debug("Runnable 완료");
        }
    }

}
