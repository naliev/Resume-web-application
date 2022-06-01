package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.JsonStreamSerializer;

public class JsonStreamPathStorageTest extends AbstractStorageTest {
    public JsonStreamPathStorageTest() {
        super(new PathStorage("/home/naliev/IdeaProjects/BaseJava/storage", new JsonStreamSerializer()));
    }
}