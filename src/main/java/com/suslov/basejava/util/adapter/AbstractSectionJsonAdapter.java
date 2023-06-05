package com.suslov.basejava.util.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstractSectionJsonAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    public static final String CLASSNAME = "CLASSNAME";
    public static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = primitive.getAsString();

        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, src.getClass().getName());
        JsonElement element = context.serialize(src);
        retValue.add(INSTANCE, element);
        return retValue;
    }
}
