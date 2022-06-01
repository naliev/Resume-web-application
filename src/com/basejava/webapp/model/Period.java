package com.basejava.webapp.model;

import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private static final long SerialVersionUID = 1L;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate from;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate to;
    private String position;
    private String description;

    public Period() {
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;
        Period period = (Period) o;
        return from.equals(period.from) && to.equals(period.to)
                && position.equals(period.position)
                && description.equals(period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, position, description);
    }

    @Override
    public String toString() {
        if (description != null) {
            return String.format("%S-%S %S%n%S",
                    from.format(DateTimeFormatter.ofPattern("MM.yy")),
                    to.format(DateTimeFormatter.ofPattern("MM.yy")),
                    position, description);
        } else {
            return String.format("%S-%S %S", from, to, position);
        }
    }
}
