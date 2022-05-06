package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String uuid1 = "N1";
    private static final String uuid2 = "N2";
    private static final String uuid3 = "N3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(new Resume(uuid1));
        storage.save(new Resume(uuid2));
        storage.save(new Resume(uuid3));
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        Assertions.assertEquals(resumes.length, storage.size());
    }

    @Test
    void get() throws NotExistStorageException {
        Assertions.assertEquals(new Resume(uuid3), storage.get(uuid3));
    }

    @Test
    void getNotExist() throws NotExistStorageException {
        NotExistStorageException e = Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy"));
        Assertions.assertEquals("Resume dummy not exist", e.getMessage());
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() throws NotExistStorageException {
        Resume r = new Resume(uuid1);
        storage.update(r);
        Assertions.assertEquals(storage.get(uuid1), r);
    }

    @Test
    void save() throws StorageException {
        storage.clear();
        Resume r = new Resume(uuid3);
        storage.save(r);
        Assertions.assertEquals(1, storage.size());
        Assertions.assertEquals(r, storage.get(uuid3));
    }

    @Test
    void saveAlreadyExist() throws StorageException {
        Resume r = new Resume(uuid3);
        ExistStorageException e = Assertions.assertThrows(ExistStorageException.class, () ->
                storage.save(r));
        Assertions.assertEquals("Resume with N3 already exist", e.getMessage());
    }

    @Test
    void storageOverflow() throws StorageException {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            Resume r = new Resume(Integer.toString(i));
            try {
                storage.save(r);
            } catch (StorageException e) {
                Assertions.fail("Storage was overflowing while filling");
            }
        }
        Resume r = new Resume("overflow");
        StorageException e = Assertions.assertThrows(StorageException.class, () ->
                storage.save(r));
        Assertions.assertEquals("Storage overflow", e.getMessage());
    }

    @Test
    void delete() throws NotExistStorageException {
        storage.delete(uuid2);
        Assertions.assertEquals(2, storage.size());
        Assertions.assertEquals(new Resume(uuid1), storage.get(uuid1));
        Assertions.assertEquals(new Resume(uuid3), storage.get(uuid3));
    }
}