package com.basejava.webapp;

public class LazySingleton {
    private static volatile LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
