package com.basejava.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Телефонный номер", "Телефон"),
    SKYPE("Skype", "Телефон"),
    EMAIL("Email", "Строка"),
    LINKEDIN("LinkedIn", "Ссылка"),
    GITHUB("GitHub", "Ссылка"),
    STACKOVERFLOW("StackOverFlow", "Ссылка"),
    HOMEPAGE("Homepage", "Ссылка");

    private final String title;
    private final String contentType;

    ContactType(String title, String contentType) {
        this.title = title;
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public String getContentType() {
        return contentType;
    }
}
