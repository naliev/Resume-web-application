package com.basejava.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long SerialVersionUID = 1L;
    private final String title;
    private final Link website;
    private final ArrayList<Period> periods = new ArrayList<>();

    public Organization(String title, Link website) {
        Objects.requireNonNull(title);
        this.title = title;
        this.website = website;
    }

    public Organization(String title, String websiteName, String websiteUrl) {
        Objects.requireNonNull(title);
        this.title = title;
        this.website = new Link(websiteName, websiteUrl);
    }

    public String getTitle() {
        return title;
    }

    public Link getWebsite() {
        return website;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (Period period : periods) {
            sb.append(period.toString());
        }
        return sb.toString();
    }
}
