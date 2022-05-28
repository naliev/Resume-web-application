package com.basejava.webapp.storage;


import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.model.Resume;

import java.io.File;

public class MainIO {
    public static void main(String[] args) {
        Resume r = ResumeTestData.newResumeWithSections("001", "Ivan Ivanov");
        ObjectStreamStorage storage = new ObjectStreamStorage(new File("/home/naliev/IdeaProjects/BaseJava/storage"));
        storage.save(r);
    }
}
