package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int ix = indexOf(resume.toString());
        if (ix != -1) {
            System.out.println(resume + " updated!");
        } else {
            System.out.println("No resume with " + resume + " uuid");
        }
    }

    public void save(Resume resume) {
        int ix = indexOf(resume.toString());
        if (ix != -1) {
            System.out.println("A resume with " + resume + " uuid is already in the storage");
            return;
        }
        if (size == storage.length) {
            System.out.println("Storage is full");
            return;
        }
        storage[size++] = resume;
    }

    public Resume get(String uuid) {
        int ix = indexOf(uuid);
        if (ix != -1) {
            return storage[ix];
        }
        System.out.println("No resume with " + uuid + " uuid");
        return null;
    }

    public void delete(String uuid) {
        int ix = indexOf(uuid);
        if (ix != -1) {
            size--;
            for (; ix < size; ix++) {
                storage[ix] = storage[ix + 1];
            }
            storage[size] = null;
        } else {
            System.out.println("No resume with " + uuid + " uuid");
        }
    }

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
