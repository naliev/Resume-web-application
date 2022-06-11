package com.basejava.webapp;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    private int moneyBag = 999;
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() ;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        MainConcurrency exemplar = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            int number = i;
            executorService.submit(() -> {
                Thread.currentThread().setName(String.format("Coin bag %S", number));
                int coins = 0;
                while (exemplar.getCoin()) {
                    coins++;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": " + coins);
                latch.countDown();
                exemplar.atomicCounter.set(exemplar.atomicCounter.get() + coins);
            });
        }

        if (latch.await(50, TimeUnit.MILLISECONDS)) {
            System.out.println("All threads successfully completed");
        } else {
            System.out.println("Not every thread manage to complete");
        }

        executorService.shutdownNow();
        System.out.println("Totally we had: " + exemplar.atomicCounter.get() + " coins");
    }

    public synchronized boolean getCoin() {
        if (moneyBag > 0) {
            moneyBag--;
            return true;
        } else {
            return false;
        }
    }



}
