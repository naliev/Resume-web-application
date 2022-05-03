package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 3;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("No resume with " + uuid + " uuid");
            return null;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("No resume with " + resume + " uuid");
        } else {
            storage[index] = resume;
            System.out.println(resume + " updated!");
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("A resume with " + resume + " uuid is already in the storage");
        } else if (size == storage.length) {
            System.out.println("Storage is full");
        } else {
            insertResume(resume, index);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("No resume with " + uuid + " uuid");
        } else {
            shiftDeletedElement(index);
            size--;
            storage[size] = null;
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void shiftDeletedElement(int index);
}
