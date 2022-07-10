package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long SerialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Period.EMPTY);
    private String title;
    private Link website;
    private List<Period> periods = new ArrayList<>();

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

    public Organization(String title, String websiteName, Period p) {
        this.title = title;
        this.website = new Link(websiteName, "");
        periods = new ArrayList<>();
        periods.add(p);
    }

    public Organization(Link website, List<Period> periods) {
        title = website.getName();
        this.website = website;
        this.periods = periods;
    }

    public Organization(String title, Link website, List<Period> periods) {
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    public String getTitle() {
        return title;
    }

    public Link getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
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
