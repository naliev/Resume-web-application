package com.basejava.webapp.model;

public enum ContactType {
    TELEPHONE_NUMBER("Телефонный номер", "Телефон"),
    SKYPE("Skype", "Телефон"),
    EMAIL("Почта", "Строка"),
    LINKEDIN_PROFILE("Профиль LinkedIn", "Ссылка"),
    GITHUB_PROFILE("Профиль GitHub", "Ссылка"),
    STACKOVERFLOW_PROFILE("Профиль StackOverFlow", "Ссылка"),
    HOMEPAGE("Домашняя страница", "Ссылка");

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
