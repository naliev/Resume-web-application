package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.util.Date;
import java.util.HashMap;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("001", "Nikita Aliev");
        resume.putContact(ContactType.EMAIL, "dudadead@gmail.com");
        resume.putContact(ContactType.GITHUB, "Naliev");
        TextSection personal = new TextSection("Easy going, polite person");
        resume.putSection(SectionType.PERSONAL, personal);
        ListSection qualificationList = new ListSection();
        qualificationList.addText("Arrays", "Collections", "jUnit 4", "Exceptions", "Reflection", "Generics", "logging", "Template method", "Singleton method");
        resume.putSection(SectionType.QUALIFICATIONS, qualificationList);
        Organization vogu = new Organization("Vologda university","");
        vogu.addPeriod(new OccupationPeriod("09/2017", "07.2021",
                "Informatics and Computer Engineering",""));
        resume.putSection(SectionType.EDUCATION, new OrganizationSection(vogu));
        //OrganizationSection experienceUniversity = new OrganizationSection(vogu);
        //experienceUniversity.addOrganization(new Organization());
        //OrganizationSection experienceExternal = new OrganizationSection("JavaOps", "05/2012 - 07/2022", "BaseJava", "Create a resume repository project");
        //resume.putSection(SectionType.EDUCATION, experienceUniversity, experienceExternal);

        printResume(resume);
    }

    public static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println("-----------------------------------------------------------------------");
        HashMap<ContactType, String> contacts = resume.getContacts();
        contacts.forEach((k, v) -> System.out.printf("%S: %S%n", k.getTitle(), v));
        System.out.println("-----------------------------------------------------------------------");
        HashMap<SectionType, Section> sections = resume.getSections();
        sections.forEach((k, v) -> System.out.printf("%S%n%S%n%n", k.getTitle(), v.toString()));
        System.out.println("-----------------------------------------------------------------------");
    }
}
