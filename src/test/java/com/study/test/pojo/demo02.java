package com.study.test.pojo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class demo02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                10,
                15,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 异步执行 - 1
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            // 业务逻辑

        }, executor);


        // 异步执行 - 2
        CompletableFuture<Void> runAsync1 = CompletableFuture.runAsync(() -> {
            // 业务逻辑

        }, executor);

        CompletableFuture.allOf(runAsync, runAsync1).get();


        executor.shutdown();


    }

}
