package com.basejava.webapp.model;

import java.util.Date;

public class OccupationPeriod {
    private final String from;
    private final String to;
    private final String position;
    private final String description;

    public OccupationPeriod(String from, String to, String position, String description) {
        this.from = from;
        this.to = to;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%S-%S %S", from, to, position);
    }
}
