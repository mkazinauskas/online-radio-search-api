package com.modzo.ors.configuration.exception.handler;

import java.util.Set;

public class DomainApiError {

    private final String id;

    private final Set<String> fields;

    private final String message;

    public DomainApiError(String id, Set<String> fields, String message) {
        this.id = id;
        this.fields = fields;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public Set<String> getFields() {
        return fields;
    }

    public String getMessage() {
        return message;
    }
}
