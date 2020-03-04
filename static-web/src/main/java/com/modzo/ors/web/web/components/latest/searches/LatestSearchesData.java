package com.modzo.ors.web.web.components.latest.searches;

import com.modzo.ors.web.web.utils.SeoText;

import java.util.List;

public class LatestSearchesData {

    private final List<Query> queries;

    public static class Query {

        private final String query;

        private final String seoQuery;

        public Query(String query) {
            this.query = query;
            this.seoQuery = SeoText.from(query);
        }

        public String getQuery() {
            return query;
        }

        public String getSeoQuery() {
            return seoQuery;
        }
    }

    public LatestSearchesData(List<Query> queries) {
        this.queries = queries;
    }

    public List<Query> getQueries() {
        return queries;
    }
}
