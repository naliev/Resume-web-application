package com.basejava.webapp;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = newResumeWithSections("001", "Nikita Aliev");
        printResume(resume);
    }

    public static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println("------Contacts------");
        StringBuilder stringBuilder = new StringBuilder();
        for (ContactType type : ContactType.values()) {
            Object contract = resume.getContact(type);
            if (contract != null) {
                stringBuilder.append(type.getTitle()).append(": ").append(contract).append("\n");
            }
        }
        System.out.println(stringBuilder);
        stringBuilder.delete(0, stringBuilder.capacity());
        for (SectionType type : SectionType.values()) {
            AbstractSection section = resume.getSection(type);
            if (section != null) {
                stringBuilder.append("------").append(type.getTitle()).append("------\n");
                stringBuilder.append(section).append("\n");
            }
        }
        System.out.println(stringBuilder);
    }

    public static Resume newResumeWithSections(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.EMAIL, "dudadead@gmail.com");
        resume.addContact(ContactType.GITHUB, "Naliev");
        resume.addSection(SectionType.PERSONAL, new TextSection("Easy-going, polite person"));
        String[] qualificationList = {"Arrays", "Collections", "jUnit 4", "Exceptions", "Reflection", "Generics",
                "logging", "Template method", "Singleton method"};
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList(qualificationList))));

        Organization vogu = new Organization("Vologda university", "vogu35", null);
        vogu.addPeriod(new Period(DateUtil.of(2017, Month.JULY), DateUtil.of(2017, Month.SEPTEMBER),
                "Preliminary Courses", null));
        vogu.addPeriod(new Period(DateUtil.of(2017, Month.SEPTEMBER), DateUtil.of(2021, Month.JUNE),
                "Informatics and Computer Engineering", ""));
        Organization javaOps = new Organization("JavaOps", "JavaOps", "https://javaops.ru/");
        javaOps.addPeriod(new Period(DateUtil.of(2022, Month.MAY), DateUtil.NOW,
                "BaseJava", "Create a resume repository project"));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>(Arrays.asList(vogu, javaOps))));

        return resume;
    }
}
