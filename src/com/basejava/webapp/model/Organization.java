package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Organization {
    private final String title;
    private String website;
    private ArrayList<Period> periods = new ArrayList<>();

    public Organization(String title, String website) {
        this.title = title;
        this.website = website;
    }

    public Organization(String title, String website, ArrayList<Period> periods) {
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void addPeriod(Period... period) {
        periods.addAll((Arrays.asList(period)));
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
