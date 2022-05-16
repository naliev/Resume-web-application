package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "N1";
    private static final String UUID_2 = "N2";
    private static final String UUID_3 = "N3";
    private static final String FULL_NAME_1 = "Nikita Aliev";
    private static final String FULL_NAME_2 = "Maxim Pavlov";
    private static final String FULL_NAME_3 = "Georgy Ivanov";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    private static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    private static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    private static final Resume RESUME_NEW = new Resume("New");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void getAllSorted() {
        assertSize(3);
        List<Resume> resumes = storage.getAllSorted();
        Assertions.assertEquals(3, resumes.size());
        Assertions.assertEquals(Arrays.asList(RESUME_3, RESUME_2, RESUME_1), resumes);
    }

    @Test
    void get() throws NotExistStorageException {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExist() throws NotExistStorageException {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() throws NotExistStorageException {
        storage.update(RESUME_1);
        Assertions.assertTrue((storage.get(RESUME_1.getUuid()) == RESUME_1));
    }

    @Test
    void updateNotExist() throws NotExistStorageException {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_NEW));
    }

    @Test
    void save() throws StorageException {
        storage.save(RESUME_NEW);
        assertSize(4);
        assertGet(RESUME_NEW);
    }

    @Test
    void saveAlreadyExist() throws StorageException {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() throws NotExistStorageException {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    void deleteNotExist() throws NotExistStorageException {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    private void assertSize(int expected) {
        Assertions.assertEquals(expected, storage.size());
    }

    private void assertGet(Resume r) throws NotExistStorageException {
        Resume expected = storage.get(r.getUuid());
        if (!r.equals(expected)) throw new NotExistStorageException(r.getUuid());
    }
}