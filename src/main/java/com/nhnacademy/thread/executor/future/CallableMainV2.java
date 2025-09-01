package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class CallableMainV2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log.debug("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());       // new MyCallable()가 블록킹 큐에 전달됨 -> todo submit 할 때, 큐에 넣을 task인스턴스를 Future(구현체는 FutureTask) 객체를 생성하고 그 안에다가 task인스턴를 넣고 나서 블락킹 큐에 넣는다.
        log.debug("future 즉시 반환, future : {}", future);

        log.debug("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 waiting");
        Integer result = future.get();      // todo 여기서 [블로킹] get() 시점에 MyCallable이 완료되었는지, 작업중인지 어떻게??, 스레드 풀에 사용 가능한 스레드가 없으면?? ->  그래서 즉시 받는건 불가능, Future라는 객체를 대신 제공 -> Future는 전달한 작업의 미래(미래 결과)이다.
        log.debug("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 runnable");

        log.debug("result : {}", result);
        log.debug("future 완료, future : {}", future);
        es.close();
    }

    static class MyCallable implements Callable<Integer> { 

        @Override
        public Integer call() throws Exception {
            log.debug("Callable 시작");
            Thread.sleep(10000);
            int value = new Random().nextInt(10);
            log.debug("create value = {}", value);
            log.debug("Callable 완료");
            return value;
        }
    }
}
