package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.model.Experience;
import com.suslov.basejava.model.Link;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.model.section.ExperienceList;
import com.suslov.basejava.model.section.Personal;
import com.suslov.basejava.model.section.SkillList;
import com.suslov.basejava.util.parser.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLSerializer implements Serializer {
    private final XMLParser xmlParser;

    public XMLSerializer() {
        xmlParser = new XMLParser(Resume.class, Link.class, ExperienceList.class, Personal.class, SkillList.class,
                Experience.class, Experience.Period.class);
    }

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws IOException {
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
