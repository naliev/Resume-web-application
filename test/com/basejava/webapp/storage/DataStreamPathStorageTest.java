package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage("/home/naliev/IdeaProjects/BaseJava/storage", new DataStreamSerializer()));
    }
}