package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureExceptionMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log.debug("작업 전달");

        Future<Integer> future = es.submit(new ExCallable());
        Thread.sleep(1000);

        try {
            log.debug("future.get() 호출 시도, future.state() : {}", future.state());
            Integer result = future.get();
            log.debug("result value = {}", result);
        } catch (ExecutionException e) {
            log.debug("e = {}", e);
            Throwable cause = e.getCause();
            log.debug("cause = {}", cause.getCause());
        }

        es.close();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log.debug("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}
