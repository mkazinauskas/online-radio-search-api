package com.modzo.ors.search.domain;

public class ReadModelException extends RuntimeException {

    private final String id;

    public ReadModelException(String id, String message) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
