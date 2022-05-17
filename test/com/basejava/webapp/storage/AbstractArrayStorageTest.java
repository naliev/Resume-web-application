package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    protected static final Resume RESUME_NEW = new Resume("New");

    protected AbstractArrayStorageTest(Storage storage) {
       super(storage);
    }

    @Test
    public void storageOverflow() throws StorageException {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            Resume r = new Resume(Integer.toString(i));
            try {
                storage.save(r);
            } catch (StorageException e) {
                Assert.fail("Storage was overflowing while filling");
            }
        }
        Assert.assertThrows(StorageException.class, () -> storage.save(RESUME_NEW));
    }
}