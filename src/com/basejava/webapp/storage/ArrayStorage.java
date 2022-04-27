package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findInStorage(resume.getUuid());
        if (index == -1) {
            System.out.println("No resume with " + resume + " uuid");
        } else {
            storage[index] = resume;
            System.out.println(resume + " updated!");
        }
    }

    public void save(Resume resume) {
        int index = findInStorage(resume.getUuid());
        if (index != -1) {
            System.out.println("A resume with " + resume + " uuid is already in the storage");
        } else if (size == storage.length) {
            System.out.println("Storage is full");
        } else {
            storage[size++] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = findInStorage(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("No resume with " + uuid + " uuid");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findInStorage(uuid);
        if (index == -1) {
            System.out.println("No resume with " + uuid + " uuid");
        } else {
            storage[index] = storage[size - 1];
            storage[--size] = null;
        }
    }

    private int findInStorage(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
