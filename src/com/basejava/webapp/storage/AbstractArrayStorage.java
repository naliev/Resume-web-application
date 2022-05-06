package com.basejava.webapp.storage;

import com.basejava.webapp.exception.*;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
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
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println(resume + " updated!");
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (size == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, index);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
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
