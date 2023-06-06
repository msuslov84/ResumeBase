package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.model.ContactType;
import com.suslov.basejava.model.Experience;
import com.suslov.basejava.model.Link;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.model.section.*;
import com.suslov.basejava.storage.file.serializer.functional.DataConsumer;
import com.suslov.basejava.storage.file.serializer.functional.DataFunction;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataSerializer implements Serializer {

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeResumeContacts(dos, resume);
            writeResumeSections(dos, resume);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws IOException {
        try (DataInputStream dis = new DataInputStream(in)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readResumeContacts(dis, resume);
            readResumeSections(dis, resume);
            return resume;
        }
    }

    private void writeResumeContacts(DataOutputStream dos, Resume resume) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        writeFromCollection(contacts.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private void writeResumeSections(DataOutputStream dos, Resume resume) throws IOException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        writeFromCollection(sections.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case OBJECTIVE:
                case PERSONAL:
                    dos.writeUTF(((Personal) entry.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeFromCollection(((SkillList) entry.getValue()).getSkills(), dos, dos::writeUTF);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeFromCollection(((ExperienceList) entry.getValue()).getExperiences(), dos, experience -> {
                        Link link = experience.getHomePage();
                        dos.writeUTF(link != null ? link.getName() : "");
                        dos.writeUTF(link != null ? link.getUrl() : "");
                        List<Experience.Period> periods = experience.getPeriods();
                        writeFromCollection(periods, dos, period -> {
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(convertFromLocalDate(period.getPeriodFrom()));
                            dos.writeUTF(convertFromLocalDate(period.getPeriodTo()));
                            dos.writeUTF(period.getDescription());
                        });
                    });
            }
        });
    }

    private <T> void writeFromCollection(Collection<T> collection, DataOutputStream out, DataConsumer<T> action)
            throws IOException {
        Objects.requireNonNull(action);
        Objects.requireNonNull(collection);
        Objects.requireNonNull(out);

        out.writeInt(collection.size());
        for (T element : collection) {
            action.accept(element);
        }
    }

    private String convertFromLocalDate(LocalDate period) {
        return period.getYear() + ";" + period.getMonthValue() + ";" + period.getDayOfMonth();
    }

    private void readResumeContacts(DataInputStream dis, Resume resume) throws IOException {
        readToCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
    }

    private void readResumeSections(DataInputStream dis, Resume resume) throws IOException {
        readToCollection(dis, () -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(sectionType, new Personal(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    SkillList skillList = new SkillList();
                    readToCollection(dis, () -> skillList.addSkill(dis.readUTF()));
                    resume.addSection(sectionType, skillList);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Experience> list = new ArrayList<>();
                    readToCollection(dis, () -> {
                        Experience experience = new Experience();
                        experience.setHomePage(new Link(dis.readUTF(), dis.readUTF()));
                        readToCollection(dis, () -> experience.addPeriod(new Experience.Period(dis.readUTF(),
                                convertToLocalDate(dis.readUTF()), convertToLocalDate(dis.readUTF()), dis.readUTF())));
                        list.add(experience);
                    });
                    resume.addSection(sectionType, new ExperienceList(list));
            }
        });
    }

    private void readToCollection(DataInputStream in, DataFunction action) throws IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            action.set();
        }
    }

    private LocalDate convertToLocalDate(String convertValue) throws IllegalArgumentException {
        String[] split = convertValue.split(";");
        if (split.length != 3) {
            throw new SerializeException("Error parsing local date value '" + convertValue + "'");
        }
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}