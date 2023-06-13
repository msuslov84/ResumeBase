package com.suslov.basejava.model;

import com.suslov.basejava.model.section.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    public static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, Personal.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, Personal.EMPTY);
        EMPTY.setSection(SectionType.ACHIEVEMENT, SkillList.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATIONS, SkillList.EMPTY);
        EMPTY.setSection(SectionType.EXPERIENCE, new ExperienceList(Experience.EMPTY));
        EMPTY.setSection(SectionType.EDUCATION, new ExperienceList(Experience.EMPTY));
    }

    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Error: Resume UUID must not be null");
        Objects.requireNonNull(fullName, "Error: Resume full name must not be null");
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

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setSection(SectionType type, AbstractSection value) {
        sections.put(type, value);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + " (" + fullName + ")";
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}