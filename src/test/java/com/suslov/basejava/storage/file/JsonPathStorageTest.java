package com.suslov.basejava.storage.file;

import com.suslov.basejava.storage.AbstractStorageTest;
import com.suslov.basejava.storage.file.serializer.JsonSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonSerializer()));
    }
}