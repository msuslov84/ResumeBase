package com.suslov.basejava.storage.file;

import com.suslov.basejava.storage.AbstractStorageTest;
import com.suslov.basejava.storage.file.serializer.ObjectSerializer;
import com.suslov.basejava.storage.file.serializer.XMLSerializer;

public class XMLPathStorageTest extends AbstractStorageTest {

    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XMLSerializer()));
    }
}