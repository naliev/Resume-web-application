package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class ListSection extends AbstractSection {
    private final ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> getList() {
        return new ArrayList<>(list);
    }

    public void addText(String... text) {
        list.addAll(Arrays.asList(text));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection that)) return false;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
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
