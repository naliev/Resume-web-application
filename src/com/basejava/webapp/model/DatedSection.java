package com.basejava.webapp.model;

public class DatedSection implements Section{
    private final String header;
    private final String period;
    private final String title;
    private String description;

    public DatedSection(String header, String period, String title) {
        this.header = header;
        this.period = period;
        this.title = title;
    }

    public DatedSection(String header, String period, String title, String description) {
        this.header = header;
        this.period = period;
        this.title = title;
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public String getPeriod() {
        return period;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return String.format("%S \n" +
                "%S %S \n" +
                "%S", header, period, title, description);
    }
}
