package com.suslov.basejava.exception;

public class ExistStorageException extends StorageException {
    private static final long serialVersionUID = 1L;

    public ExistStorageException(String uuid) {
        super("Error: resume [" + uuid + "] already exists", uuid);
    }

    public ExistStorageException(Exception e) {
        super("Error: resume already exists", e);
    }

    public ExistStorageException(String uuid, Exception e) {
        super("Error: resume [" + uuid + "] already exists", uuid, e);
    }
}
