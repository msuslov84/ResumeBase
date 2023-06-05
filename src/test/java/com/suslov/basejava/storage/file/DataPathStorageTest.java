package com.suslov.basejava.storage.file;

import com.suslov.basejava.storage.AbstractStorageTest;
import com.suslov.basejava.storage.file.serializer.DataSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataSerializer()));
    }
}