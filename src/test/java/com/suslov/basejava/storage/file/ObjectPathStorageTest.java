package com.suslov.basejava.storage.file;

import com.suslov.basejava.storage.AbstractStorageTest;
import com.suslov.basejava.storage.file.serializer.ObjectSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectSerializer()));
    }
}