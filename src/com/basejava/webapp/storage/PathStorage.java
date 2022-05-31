package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.IOStrategy;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final IOStrategy strategy;

    protected PathStorage(String dir, IOStrategy strategy) {
        Objects.requireNonNull(dir, "directory mustn't be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new StorageException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    protected List<Resume> doGetAll() {
        ArrayList<Resume> resumes = new ArrayList<>();
        getFiles(directory).forEach(path -> resumes.add(doGet(path)));
        return resumes;
    }

    @Override
    public void clear() {
        getFiles(directory).forEach(this::doDelete);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException(path + "is cannot be read", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException(path + " is cannot be updated", r.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException(path + " is cannot be saved", r.getUuid(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException(path + " is cannot be deleted", getFileName(path), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    public int size() {
        return (int) getFiles(directory).count();
    }

    private Stream<Path> getFiles(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException(directory + " pathname does not denote a directory, or an I/O error occurs", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}