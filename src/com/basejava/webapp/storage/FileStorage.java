package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.strategies.IOStrategy;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final IOStrategy strategy;

    FileStorage(File directory, IOStrategy strategy) {
        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException((directory.getAbsolutePath() + "is not a directory"));
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writeable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = getFiles(directory);
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(file.toPath())));
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + "is cannot be read", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(file.toPath())));
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + " is cannot be updated", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException(file.getAbsolutePath() + " is cannot be saved", r.getUuid(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getAbsolutePath() + " is cannot be deleted", file.getName());
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
        return getFiles(directory).length;
    }

    @Override
    public void clear() {
        File[] files = getFiles(directory);
        for (File file : files) {
            doDelete(file);
        }
    }

    private File[] getFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory.getAbsolutePath() + " pathname does not denote a directory, or an I/O error occurs");
        }
        return files;
    }
}
