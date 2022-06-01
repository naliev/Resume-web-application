package com.basejava.webapp.storage;

import com.basejava.webapp.storage.strategy.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage("/home/naliev/IdeaProjects/BaseJava/storage", new XmlStreamSerializer()));
    }
}