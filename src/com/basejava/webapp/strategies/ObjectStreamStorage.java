package com.basejava.webapp.strategies;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements IOStrategy {

    @Override
    public void doWrite(Resume r, OutputStream stream) {
        try (ObjectOutputStream oos = new ObjectOutputStream(stream)) {
            oos.writeObject(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume doRead(InputStream stream) {
        try (ObjectInputStream ois = new ObjectInputStream(stream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}