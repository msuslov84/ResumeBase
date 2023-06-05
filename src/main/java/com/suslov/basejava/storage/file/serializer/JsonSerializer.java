package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.model.Resume;
import com.suslov.basejava.util.parser.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonSerializer implements Serializer {

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws IOException {
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws IOException {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
