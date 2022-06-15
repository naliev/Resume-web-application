package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ListStorage extends AbstractStorage<Integer> {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume value, Integer searchKey) {
        storage.set(searchKey, value);
    }

    @Override
    protected void doSave(Resume value, Integer searchKey) {
        storage.add(value);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey != null);
    }

    public Stream<Resume> stream() {
        return new ArrayList<>(storage).stream();
    }
}
