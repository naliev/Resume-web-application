package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    private static final Resume RESUME_NEW = new Resume("New");

    protected AbstractArrayStorageTest(Storage storage) {
       super(storage);
    }

    @Test
    void storageOverflow() throws StorageException {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            Resume r = new Resume(Integer.toString(i));
            try {
                storage.save(r);
            } catch (StorageException e) {
                Assertions.fail("Storage was overflowing while filling");
            }
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(RESUME_NEW));
    }
}