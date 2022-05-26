package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException((directory.getAbsolutePath() + "is not a directory"));
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writeable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected List<Resume> doGetAll() {
        return null;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + "is cannot be read", "unknown");
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + "is cannot be updated", r.getUuid());
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + "is cannot be saved", r.getUuid());
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getAbsolutePath() + "is cannot be deleted", doGet(file).getUuid());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public int size() {
        return getFilesFromDirectory(directory).length;
    }

    @Override
    public void clear() {
        File[] files = getFilesFromDirectory(directory);
        for (File file : files) {
            doDelete(file);
        }
    }

    private File[] getFilesFromDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory.getAbsolutePath() + "is not appropriate directory", "unknown");
        } else {
            return files;
        }
    }
}
