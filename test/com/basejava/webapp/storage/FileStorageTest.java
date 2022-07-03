package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.ObjectStreamSerializer;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(Config.getConfig().getStorageDir(), new ObjectStreamSerializer()));
    }
}