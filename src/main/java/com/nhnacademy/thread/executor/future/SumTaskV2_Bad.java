package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class SumTaskV2_Bad {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        // todo submit 하고 각각 get 하면 결국엔 get에서 블록킹 걸리는 시간이 똑같다.
        Future<Integer> future1 = es.submit(task1);
        Integer sum1 = future1.get();

        Future<Integer> future2 = es.submit(task2);
        Integer sum2 = future2.get();

        log.debug("task1 : {}", sum1);
        log.debug("task2 : {}", sum2);

        int sumAll = sum1 + sum2;
        log.debug("sumAll : {}", sumAll);
        log.debug("end");

        es.close();
    }

    static class SumTask implements Callable<Integer> {

        int start;
        int end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            log.debug("call() 시작");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            log.debug("결과 sum : {}", sum);
            log.debug("call() 종료");
            return sum;
        }
    }

}
