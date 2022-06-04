package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage("/home/naliev/IdeaProjects/BaseJava/storage", new ObjectStreamSerializer()));
    }
}