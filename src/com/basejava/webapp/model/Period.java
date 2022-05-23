package com.basejava.webapp.model;

public class Period {
    private final String from;
    private final String to;
    private final String position;
    private final String description;

    public Period(String from, String to, String position, String description) {
        this.from = from;
        this.to = to;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        if (description != null) {
            return String.format("%S-%S %S%n%S", from, to, position, description);
        } else {
            return String.format("%S-%S %S", from, to, position);
        }
    }
}
