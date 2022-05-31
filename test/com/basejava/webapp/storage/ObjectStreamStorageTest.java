package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.ObjectStreamStorage;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(new File("/home/naliev/IdeaProjects/BaseJava/storage"), new ObjectStreamStorage()));
    }
}