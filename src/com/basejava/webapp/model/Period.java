package com.basejava.webapp.model;

import java.time.LocalDate;

public class Period {
    private final LocalDate from;
    private final LocalDate to;
    private final String position;
    private final String description;

    public Period(LocalDate from, LocalDate to, String position, String description) {
        this.from = from;
        this.to = to;
        this.position = position;
        this.description = description;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
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
