package com.basejava.webapp.model;

import java.util.ArrayList;

public class Organization {
    private final String title;
    private final String website;
    private final ArrayList<Period> periods = new ArrayList<>();

    public Organization(String title, String website) {
        this.title = title;
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (Period period : periods) {
            sb.append(period.toString()).append("\n");
        }
        return sb.toString();
    }
}
