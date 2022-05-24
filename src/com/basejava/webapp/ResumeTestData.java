package com.basejava.webapp;

import com.basejava.webapp.model.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("001", "Nikita Aliev");
        //resume.putContact(ContactType.EMAIL, "dudadead@gmail.com");
        //resume.putContact(ContactType.GITHUB, "Naliev");
        //TextSection personal = new TextSection("Easy going, polite person");
        //resume.putSection(SectionType.PERSONAL, personal);
        //ListSection qualificationList = new ListSection();
        //qualificationList.addText("Arrays", "Collections", "jUnit 4", "Exceptions", "Reflection", "Generics", "logging", "Template method", "Singleton method");
        //resume.putSection(SectionType.QUALIFICATIONS, qualificationList);
        //Organization vogu = new Organization("Vologda university", "");
        //vogu.addPeriod(new Period("09/2017", "07.2021",
        //        "Informatics and Computer Engineering", ""));
        //Organization javaOps = new Organization("JavaOps", "https://javaops.ru/");
        //javaOps.addPeriod(new Period("05/2012", "07/2022",
        //        "BaseJava", "Create a resume repository project"));
        //resume.putSection(SectionType.EDUCATION, new OrganizationSection(vogu, javaOps));

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
        stringBuilder.setLength(stringBuilder.length() - 1);
        System.out.println(stringBuilder);
        stringBuilder.delete(0, stringBuilder.capacity());
        resume.getSections().forEach((sectionType, section) -> {
            stringBuilder.append("------").append(sectionType.getTitle()).append("------\n");
            stringBuilder.append(section.toString()).append("\n");
        });
        System.out.println(stringBuilder);
    }
}
