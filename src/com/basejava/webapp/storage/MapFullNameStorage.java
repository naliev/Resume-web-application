package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapFullNameStorage extends AbstractStorage {
    protected final Map<Object, Resume> storage = new TreeMap<>();

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
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
    public void save(Resume r) {
        Object searchKey = getSearchKey(r.getFullName());
        doSave(r, searchKey);
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
    protected Object getSearchKey(String fullName) {
        return fullName;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
