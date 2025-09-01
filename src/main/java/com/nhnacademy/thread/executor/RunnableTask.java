package com.nhnacademy.thread.executor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableTask implements Runnable {

    private final String name;
    private int sleepMs = 1000;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        log.debug(name + "시작");
        try {
            Thread.sleep(sleepMs);      // 작업 시간 시뮬
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug(name + "완료");
    }
}
