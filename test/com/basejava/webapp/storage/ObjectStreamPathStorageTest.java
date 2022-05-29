package com.basejava.webapp.storage;

import com.basejava.webapp.strategies.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage("/home/naliev/IdeaProjects/BaseJava/storage", new ObjectStreamStorage()));
    }
}