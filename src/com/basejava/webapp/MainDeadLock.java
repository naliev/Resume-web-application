package com.basejava.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        Storage storage1 = new Storage();
        new Thread(() -> {
            try {
                storage1.make();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                storage1.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    static class Storage {
        final String maker = "Maker";
        final Object taker = "Taker";

        public  void make() throws InterruptedException {
            synchronized (maker) {
                System.out.println(Thread.currentThread().getName() + " blocked a maker");
                Thread.sleep(100);
                synchronized (taker) {
                    System.out.println(Thread.currentThread().getName() + " blocked a taker");
                }
            }
        }

        public  void take() throws InterruptedException {
            synchronized (taker) {
                System.out.println(Thread.currentThread().getName() + " blocked a taker");
                Thread.sleep(100);
                synchronized (maker) {
                    System.out.println(Thread.currentThread().getName() + " blocked a maker");
                }
            }
        }
    }
}
