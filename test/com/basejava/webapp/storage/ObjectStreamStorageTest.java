package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.ObjectStreamSerializer;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(Config.getConfig().getStorageDir(), new ObjectStreamSerializer()));
    }
}