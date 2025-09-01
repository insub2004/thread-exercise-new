package com.nhnacademy.thread.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int poolSize = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size(); // 작업 대기 큐
            long completedTask = poolExecutor.getCompletedTaskCount(); // 스레드가 완료한 작업의 수
            log.debug("[pool={}, active={}, queuedTasks={}, completedTask={}", poolSize, active, queuedTasks, completedTask);
        } else {
            log.debug(executorService.toString());
        }
    }

}
