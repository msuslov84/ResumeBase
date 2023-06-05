package com.suslov.basejava.exception;

public class StorageException extends RuntimeException {

    private String uuid;

    public StorageException(String message, String uuid, Exception e) {
        this(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public StorageException(String message, String uuid) {
        this(message);
        this.uuid = uuid;
    }

    public StorageException(String message) {
        super(message);
    }

    public String getUuid() {
        return uuid;
    }
}