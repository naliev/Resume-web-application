package com.basejava.webapp;

import java.util.ArrayList;

public class MainConcurrent {
    int moneyBag = 999;

    public static void main(String[] args) throws InterruptedException {

        MainConcurrent exemplar = new MainConcurrent();

        ArrayList<Thread> threadList = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            int number = i;

            Thread thread = new Thread(() -> {
                Thread.currentThread().setName(String.format("Coin bag %S", number));
                int coins = 0;
                while (exemplar.getCoin()) {
                    coins++;
                }
                System.out.println(Thread.currentThread().getName() + ": " + coins);
            });
            thread.start();
            threadList.add(thread);
        }

        for (Thread th : threadList) {
            th.join();
        }
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
