package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class CallableMainV1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());       // new MyCallable()가 블록킹 큐에 전달됨
        Integer result = future.get();                              // todo 여기서 get() 시점에 MyCallable이 완료되었는지, 작업중인지 어떻게??, 스레드 풀에 사용 가능한 스레드가 없으면??
                                                                    // -> 그래서 즉시 받는건 불가능, Future라는 객체를 대신 제공 -> Future는 전달한 작업의 미래(미래 결과)이다.
        log.debug("result : {}", result);
        es.close();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log.debug("Callable 시작");
            Thread.sleep(2000);
            int value = new Random().nextInt(10);
            log.debug("create value = {}", value);
            log.debug("Callable 완료");
            return value;
        }
    }
}
