package com.basejava.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection implements Serializable {
    private static final long SerialVersionUID = 1L;
    private ArrayList<String> list;

    public ListSection() {
    }

    public ListSection(ArrayList<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        if (list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (String text : list) {
            builder.append(text).append("\n");
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }
}
