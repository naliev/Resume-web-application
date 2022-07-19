package com.basejava.webapp;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = newResumeWithSectionsNaliev("001", "Nikita Aliev");
        printResume(resume);
    }

    public static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println("------Contacts------");
        StringBuilder stringBuilder = new StringBuilder();
        for (ContactType type : ContactType.values()) {
            Object contact = resume.getContact(type);
            if (contact != null) {
                stringBuilder.append(type.getTitle()).append(": ").append(contact).append("\n");
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

    public static Resume newResumeWithSectionsNaliev(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.EMAIL, "dudadead@gmail.com");
        resume.addContact(ContactType.PHONE_NUMBER, "+7 981 506 38 81");
        resume.addContact(ContactType.GITHUB, "https://github.com/naliev");
        resume.addSection(SectionType.PERSONAL, new TextSection("Responsible, polite and just a good person. I am always looking to learn new things."));
        String[] qualificationList = {"Object model", "Collections", "I/O system", "Serialization", "PostgreSQL", "Servlets",
                "JSP/JSTL", "Web containers", "JUnit 4", "Git"};
        String[] achievementList = {"Developed a warehouse logistics system - accounting for goods, automation of warehouse accounting.",
                "Organized web integration with the \"Mercury\" system, which later \"Mercury\" itself bought from me",
                "Developed a large accounting system for a foreign customer"
        };
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievementList));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualificationList));

        Organization vogu = new Organization("Vologda university", "Vologda university", "https://vogu35.ru/");
        vogu.addPeriod(new Period(DateUtil.of(2017, Month.JULY), DateUtil.of(2017, Month.SEPTEMBER),
                "Preliminary Courses", ""));
        vogu.addPeriod(new Period(DateUtil.of(2017, Month.SEPTEMBER), DateUtil.of(2021, Month.JUNE),
                "Undergraduate","Informatics and Computer Engineering"));
        Organization javaOps = new Organization("JavaOps", "JavaOps", "https://javaops.ru/");
        javaOps.addPeriod(new Period(DateUtil.of(2022, Month.MAY), DateUtil.of(2022, Month.JULY),
                "BaseJava", "Created a web application of resume database"));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>(Arrays.asList(vogu, javaOps))));

        Organization rarus = new Organization("Rarus International", "Rarus International", "https://1c-rarus.com/");
        rarus.addPeriod(new Period(DateUtil.of(2021, Month.SEPTEMBER), DateUtil.NOW, "Developer",
                "Development and support of domestic and foreign solutions on the 1C: Enterprise platform."));

        return resume;
    }

    public static Resume newResumeWithSectionsPavlov(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.EMAIL, "Pavlov@yandex.ru");
        resume.addContact(ContactType.GITHUB, "Pavlov");
        resume.addSection(SectionType.PERSONAL, new TextSection("Confident, ambitious man"));
        String[] qualificationList = {"Java core", "Unit testing", "Spring", "Hibernate"};
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList(qualificationList))));

        Organization mgu = new Organization("Moscow government university", "Moscow government university", "msu.ru");
        mgu.addPeriod(new Period(DateUtil.of(2011, Month.SEPTEMBER), DateUtil.of(2015, Month.MAY),
                "Undergraduate", "Creation and automation of computing systems"));
        mgu.addPeriod(new Period(DateUtil.of(2015, Month.SEPTEMBER), DateUtil.of(2021, Month.JUNE),
                "Graduate school", "Creation and automation of computing systems"));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>(Collections.singletonList(mgu))));
        Organization epam = new Organization("EPAM Systems","Enterprise software development", "epam.com");
        epam.addPeriod(new Period(DateUtil.of(2015, Month.DECEMBER), DateUtil.NOW,
                "developer","Network related applications developer")
        );
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<>(Collections.singletonList(epam))));
        return resume;
    }

    public static Resume newResumeWithSectionsAvdeeva(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.EMAIL, "AvdeevaIrina@mail.ru");
        resume.addContact(ContactType.GITHUB, "AvIr");
        resume.addSection(SectionType.PERSONAL, new TextSection("Cheerful and big hearted"));
        String[] qualificationList = {"Python core", "Django", "Laravel", "Kafka"};
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList(qualificationList))));

        Organization vogu = new Organization("Saints petersburg university", "Saints petersburg university", "");
        vogu.addPeriod(new Period(DateUtil.of(2015, Month.SEPTEMBER), DateUtil.of(2020, Month.JUNE),
                "Undergraduate","Informatics and Computer Engineering"));
        Organization javaOps = new Organization("JavaOps", "JavaOps", "https://javaops.ru/");
        javaOps.addPeriod(new Period(DateUtil.of(2018, Month.MAY), DateUtil.of(2018, Month.AUGUST),
                "TopJava", "Create a resume repository project"));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new ArrayList<>(Arrays.asList(vogu, javaOps))));

        return resume;
    }
}
