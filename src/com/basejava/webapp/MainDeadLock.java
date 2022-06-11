package com.basejava.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        Storage storage1 = new Storage();
        Thread thread1 = storage1.createAndRun("maker", "taker");
        Thread thread2 = storage1.createAndRun("taker", "maker");
    }

    static class Storage {

        public Thread createAndRun(String firstResource, String secondResource) {
            Thread th = new Thread(() -> {
                try {
                    synchronized (firstResource) {
                        System.out.println(Thread.currentThread().getName() + " blocked a " + firstResource);
                        Thread.sleep(10);
                        synchronized (secondResource) {
                            System.out.println(Thread.currentThread().getName() + " blocked a " + secondResource);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            th.start();
            return th;
        }
    }
}
