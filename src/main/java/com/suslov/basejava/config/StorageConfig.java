package com.suslov.basejava.config;

import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.storage.Storage;
import com.suslov.basejava.storage.sql.SqlStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StorageConfig {
    public static final String PROPERTIES_FILE = "/config.properties";
    private static final StorageConfig INSTANCE = new StorageConfig();

    private final File storageDir;
    private final Storage storage;

    private StorageConfig() {
        try (InputStream in = StorageConfig.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(in);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
//            storage = new ListStorage();
        } catch (IOException e) {
            throw new StorageException("Error reading storage configuration file '" + PROPERTIES_FILE + "'");
        }
    }

    public static StorageConfig getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
