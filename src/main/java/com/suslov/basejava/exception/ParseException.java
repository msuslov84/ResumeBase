package com.suslov.basejava.exception;

public class ParseException extends SerializeException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Exception cause) {
        super(message, cause);
    }
}
