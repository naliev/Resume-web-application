package com.basejava.webapp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showDeepDirList(dir, "");
    }

    public static ArrayList<File> getDeepDirList(File directory) {
        ArrayList<File> files = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                ArrayList<File> deeperFiles = getDeepDirList(file);
                files.addAll(deeperFiles);
            } else {
                files.add(file);
            }
        }
        return files;
    }

    public static void showDeepDirList(File directory, String level) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                System.out.println(level + "|---" + file.getName());
                showDeepDirList(file, (level + "|   "));
            } else {
                System.out.println(level + "|---" + file.getName());
            }
        }
    }

}
