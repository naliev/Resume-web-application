package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class OrganizationSection extends AbstractSection {
    private final ArrayList<Organization> organizations;

    public OrganizationSection(Organization... organization) {
        this.organizations = new ArrayList<>(Arrays.asList(organization));
    }

    public ArrayList<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection that)) return false;

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
            sb.append(String.format("%S", org.toString()));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
