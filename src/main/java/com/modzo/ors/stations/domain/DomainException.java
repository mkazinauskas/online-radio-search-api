package com.modzo.ors.stations.domain;

import org.springframework.util.Assert;

import java.util.Set;

import static com.nimbusds.oauth2.sdk.util.CollectionUtils.isNotEmpty;

public class DomainException extends RuntimeException {

    private final String id;

    private final Set<String> fields;

    public DomainException(String id, String field, String message) {
        super(message);
        this.id = id;
        this.fields = Set.of(field);
    }

    public DomainException(String id, Set<String> fields, String message) {
        super(message);
        this.id = id;
        Assert.isTrue(isNotEmpty(fields), "Fields has to be set");
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public Set<String> getFields() {
        return fields;
    }
}