package com.nhnacademy.thread.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.nhnacademy.thread.executor.ExecutorUtils.printState;

@Slf4j
public class ExecutorBasicMain {
    public static void main(String[] args) {
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        log.debug("== 초기 상태 ==");
        printState(es);

        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));      // 최초 작업 2개까지는 풀에 스레드가 없으니깐 스레드를 만든다.
        es.execute(new RunnableTask("taskC"));      // 이 다음부터는 2개를 계속 재사용
        es.execute(new RunnableTask("taskD"));
        log.debug("== 작업 수행 중 ==");
        printState(es);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("== 작업 수행 완료 ==");
        printState(es);     // active = 0  => 2개의 스레드가 쉬고 있음

        es.close();
        log.debug("== shutdown 완료 ==");      // pool=0 => 2개의 스레드 제거 완료
        printState(es);
    }
}
