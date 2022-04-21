package com.startjava.lesson_1;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        int inputIndex = 0;
        for (int i = storage.length - 1; i >= 0; i--) {
            if (storage[i] != null) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Resume with this uuid already exist!");
                    return;
                }
            } else {
                inputIndex = i;
            }
        }
        storage[inputIndex] = r;
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if ((resume != null) && (resume.uuid.equals(uuid))) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if ((storage[i] != null) && (storage[i].uuid.equals(uuid))) {
                storage[i] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int count = 0;
        Resume[] tmpStorage = new Resume[storage.length];
        for (Resume r : storage) {
            if (r != null) tmpStorage[count++] = r;
        }
        return Arrays.copyOf(tmpStorage, count);
    }

    int size() {
        int count = 0;
        for (Resume r : storage) {
            if (r != null) count++;
        }
        return count;
    }
}
