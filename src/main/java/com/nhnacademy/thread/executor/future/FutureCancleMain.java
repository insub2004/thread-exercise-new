package com.nhnacademy.thread.executor.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureCancleMain {

//    private static boolean mayInterruptIfRunning = true;   // 작업이 실행 중이면 바로 interrupt로 작업중인 것 종료 -> future 값을 얻을 수 없다. (예외 발생)
    private static boolean mayInterruptIfRunning = false;    // 작업하고 있는 것은 작업하게 놔둔다(인터럽트 발생 안함) -> future 값을 얻을 수 없다. (예외 발생)

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(3);

        Future<String> future = es.submit(new MyTask());
        Future<String> future1 = es.submit(new MyTask());
        Future<String> future2 = es.submit(new MyTask());

        log.debug("future state : {}", future.state());
        log.debug("future state : {}", future1.state());
        log.debug("future state : {}", future2.state());

        //일정 시간 뒤 일부로 취소 시키기
        Thread.sleep(3000);

        // cancel() 호출
        log.debug("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        boolean cancelResult1 = future1.cancel(mayInterruptIfRunning);

        log.debug("cancle(" + mayInterruptIfRunning + ") result : " + cancelResult);
        log.debug("cancle(" + mayInterruptIfRunning + ") result : " + cancelResult1);

        // 결과 확인
        try {
            log.debug("future result : " + future.get());
            log.debug("future result : " + future1.get());
            log.debug("future result : " + future2.get());
        } catch (CancellationException e) {     // todo cancel() 한 것에 get()을 시도하면 값을 못 얻고 이 에러가 나옴
            log.debug("future는 이미 취소 되었습니다.");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        es.close();
    }

    static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    log.debug("작업 중 : " + i);
                    Thread.sleep(1000);  // 1초 sleep
                }
            } catch (InterruptedException e) {
                log.debug("인터럽트 발생!");
                return "Interrupted";
            }
            return "Completed";
        }
    }
}
