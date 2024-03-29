package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.ArrayStorage;
import com.basejava.webapp.storage.ListStorage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ListStorage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Georgy Ivanov");
        Resume r2 = new Resume("uuid2", "Maxim Pavlov");
        Resume r3 = new Resume("uuid3", "Nikita Aliev");

        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);

        ARRAY_STORAGE.update(r2);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.toString());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
