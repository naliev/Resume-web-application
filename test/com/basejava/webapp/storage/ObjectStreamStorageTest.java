package com.basejava.webapp.storage;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File("/home/naliev/IdeaProjects/BaseJava/storage")));
    }
}