package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class OrganizationSection extends Section{
    private ArrayList<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization ... organization) {
        this.organizations = new ArrayList<>(Arrays.asList(organization));
    }

    public void addOrganization(Organization ... org) {
        organizations.addAll(Arrays.asList(org));
    }

    public ArrayList<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Organization org:organizations) {
            sb.append(String.format("%S", org.toString()));
        }
        return sb.toString();
    }
}
