package com.basejava.webapp.storage;


import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.DataStreamSerializer;

import java.io.File;

public class MainIO {
    public static void main(String[] args) {
        Resume r = ResumeTestData.newResumeWithSections("001", "Ivan Ivanov");
        FileStorage storage = new FileStorage(new File("/home/naliev/IdeaProjects/BaseJava/storage"), new DataStreamSerializer());
        storage.save(r);
        System.out.println(storage.get(r.getUuid()));
    }
}
