package com.suslov.basejava.model.section;

public enum SectionType {
    OBJECTIVE("Position"),
    PERSONAL("Personal qualities"),
    ACHIEVEMENT("Achievements"),
    QUALIFICATIONS("Qualification"),
    EXPERIENCE("Experience"),
    EDUCATION("Education");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
