package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Collection;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected final TreeMap<Object, Resume> storage = new TreeMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> values = storage.values();
        return values.toArray(new Resume[0]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume value, Object searchKey) {
        storage.put(searchKey, value);
    }

    @Override
    protected void doSave(Resume value, Object searchKey) {
        storage.put(searchKey, value);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (storage.containsKey(searchKey));
    }
}