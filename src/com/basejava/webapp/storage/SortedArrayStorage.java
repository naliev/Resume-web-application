package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findIndex(String uuid) {
        Resume keySearch = new Resume();
        keySearch.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, keySearch);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        index = -index - 1;
        if (index < size) {
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = resume;
    }

    @Override
    protected void shiftDeletedElement(int index) {
        if (index != size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
    }
}
