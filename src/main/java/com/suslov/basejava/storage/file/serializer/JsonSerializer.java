package com.suslov.basejava.storage.file.serializer;

import com.google.gson.JsonParseException;
import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.util.parser.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonSerializer implements Serializer {

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws SerializeException {
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        } catch (JsonParseException | IOException exp) {
            throw new SerializeException("Error JSON serialization to file resume '" + resume + "'", exp);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws SerializeException {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        } catch (JsonParseException | IOException exp) {
            throw new SerializeException("Error JSON deserialization from file", exp);
        }
    }
}
