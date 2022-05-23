package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class ListSection extends Section {
    private final ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> getList() {
        return new ArrayList<>(list);
    }

    public void addText(String... text) {
        list.addAll(Arrays.asList(text));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String text : list) {
            builder.append("*").append(text).append("\n");
        }
        return builder.toString();
    }
}
