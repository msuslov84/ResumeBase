package com.suslov.basejava.exception;

public class NotExistStorageException extends StorageException {
    private static final long serialVersionUID = 1L;

    public NotExistStorageException(String uuid) {
        super("Error: resume [" + uuid + "] not exists", uuid);
    }

    public NotExistStorageException(String uuid, Exception e) {
        super("Error: resume [" + uuid + "] not exists", uuid, e);
    }
}
