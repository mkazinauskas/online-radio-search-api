package com.modzo.ors.stations.domain;

public class DomainException extends RuntimeException {

    private final String id;

    public DomainException(String id, String message) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}