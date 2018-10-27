package com.mozdzo.ors.search;

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
