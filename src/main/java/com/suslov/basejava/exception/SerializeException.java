package com.suslov.basejava.exception;

public class SerializeException extends StorageException {

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Exception cause) {
        super(message, cause);
    }
}
