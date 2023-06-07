package com.suslov.basejava.util.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suslov.basejava.model.section.AbstractSection;
import com.suslov.basejava.util.adapter.AbstractSectionJsonAdapter;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new AbstractSectionJsonAdapter<>())
            .setPrettyPrinting()
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> T read (String content, Class<T> clazz) {
        return GSON.fromJson(content, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
