package com.modzo.ors.configuration.exception.handler;

public class DomainApiError {

    private final String id;

    private final String message;

    public DomainApiError(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
