package com.basejava.webapp.storage;

import com.basejava.webapp.exception.*;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(Resume value, Integer searchKey) {
        storage[searchKey] = value;
        System.out.println(value + " updated!");
    }

    @Override
    protected void doSave(Resume value, Integer searchKey) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", value.getUuid());
        } else {
            insertResume(value, searchKey);
            size++;
        }
    }

    @Override
    protected void doDelete(Integer searchKey) {
        fillDeletedElement(searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return findIndex(uuid);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        if (searchKey >= 0) {
            return !(storage[searchKey] == null);
        } else {
            return false;
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
