package com.basejava.webapp.model;

import java.util.Map;
import java.util.HashMap;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    final private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

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

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void putContact(ContactType contactType, String contract) {
        contacts.put(contactType, contract);
    }

    public void getSection(SectionType sectionType) {
        sections.get(sectionType);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void putSection(SectionType sectionType, Section section) {
        sections.putIfAbsent(sectionType, section);
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
