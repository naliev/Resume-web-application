package com.basejava.webapp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection implements Serializable {
    private static final long SerialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Period.EMPTY);
    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org.toString());
        }
        return sb.toString();
    }
}
