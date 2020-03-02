package com.modzo.ors.web.web.components.latest.searches;

import java.util.List;

public class LatestSearchesData {

    private final List<String> queries;

    public LatestSearchesData(List<String> queries) {
        this.queries = queries;
    }

    public List<String> getQueries() {
        return queries;
    }
}
