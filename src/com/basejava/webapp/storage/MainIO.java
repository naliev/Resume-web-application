package com.basejava.webapp.storage;


import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.ObjectStreamStorage;

import java.io.File;

public class MainIO {
    public static void main(String[] args) {
        Resume r = ResumeTestData.newResumeWithSections("001", "Ivan Ivanov");
        FileStorage storage = new FileStorage(new File("/home/naliev/IdeaProjects/BaseJava/storage"), new ObjectStreamStorage());
        storage.save(r);
    }
}
