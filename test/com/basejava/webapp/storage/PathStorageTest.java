package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.strategy.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(Config.getConfig().getStorageDir().getAbsolutePath(), new ObjectStreamSerializer()));
    }
}