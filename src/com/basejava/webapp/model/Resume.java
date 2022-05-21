package com.basejava.webapp.model;

import java.util.HashMap;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    final private String uuid;
    private String fullName;
    private final HashMap<ContactType, String> contacts = new HashMap<>();
    private final HashMap<SectionType, Section> sections = new HashMap<>();

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void putContact(ContactType contactType, String contract) {
        contacts.put(contactType, contract);
    }

    public HashMap<ContactType, String> getContacts() {
        return new HashMap<>(contacts);
    }

    public void putSection(SectionType sectionType, Section ... content) {
        for (Section element:content) {
            sections.put(sectionType, element);
        }
    }

    public HashMap<SectionType, Section> getSections() {
        return new HashMap<>(sections);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
