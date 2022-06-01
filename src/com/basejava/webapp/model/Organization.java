package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long SerialVersionUID = 1L;
    private String title;
    private Link website;
    private final ArrayList<Period> periods = new ArrayList<>();

    public Organization() {
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return title.equals(that.title) && website.equals(that.website) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
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
