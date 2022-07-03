package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapUuidStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        XmlStreamPathStorageTest.class,
        DataStreamPathStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
