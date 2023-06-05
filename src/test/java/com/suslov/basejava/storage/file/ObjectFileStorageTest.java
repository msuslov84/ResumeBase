package com.suslov.basejava.storage.file;

import com.suslov.basejava.storage.AbstractStorageTest;
import com.suslov.basejava.storage.file.serializer.ObjectSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializer()));
    }
}