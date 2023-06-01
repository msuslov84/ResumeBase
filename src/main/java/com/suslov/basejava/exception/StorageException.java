package com.suslov.basejava.exception;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        this(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public String getUuid() {
        return uuid;
    }
}