package com.modzo.ors.web.web.components;

public enum ComponentType {
    LATEST_SEARCHES("latestSearches"),
    PAGE_TITLE("pageTitle");

    private final String type;

    ComponentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
