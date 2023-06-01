package com.suslov.basejava.model;

public enum ContactType {
    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Эл. почта"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverFlow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
