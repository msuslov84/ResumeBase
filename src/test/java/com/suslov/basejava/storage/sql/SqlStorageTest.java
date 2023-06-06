package com.suslov.basejava.storage.sql;

import com.suslov.basejava.config.StorageConfig;
import com.suslov.basejava.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(StorageConfig.getInstance().getStorage());
    }
}
