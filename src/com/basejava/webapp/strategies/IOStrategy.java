package com.basejava.webapp.strategies;

import com.basejava.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {
    void doWrite(Resume r, OutputStream os);

    Resume doRead(InputStream is);
}
