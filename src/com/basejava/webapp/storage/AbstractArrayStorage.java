package com.basejava.webapp.storage;

import com.basejava.webapp.exception.*;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
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

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(Resume value, Object searchKey) {
        storage[(int) searchKey] = value;
        System.out.println(value + " updated!");
    }

    @Override
    protected void doSave(Resume value, Object searchKey) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", value.getUuid());
        } else {
            insertResume(value, (int) searchKey);
            size++;
        }
    }

    @Override
    protected void doDelete(Object searchKey) {
        shiftDeletedElement((int) searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return findIndex(uuid);
    }

    @Override
    protected boolean isValueExist(Object searchKey) {
        if ((int) searchKey >= 0) {
            return !(storage[(int) searchKey] == null);
        } else return false;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void shiftDeletedElement(int index);
}
