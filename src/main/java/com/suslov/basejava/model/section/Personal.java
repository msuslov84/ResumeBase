package com.suslov.basejava.model.section;

import java.util.Objects;

public class Personal extends AbstractSection {
    public static final long serialVersionUID = 1L;

    public static final Personal EMPTY = new Personal("");

    private String content;

    public Personal() {
    }

    public Personal(String content) {
        this.content = Objects.requireNonNull(content, "Error: personal content must not be null").trim();
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personal personal = (Personal) o;
        return Objects.equals(content, personal.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
