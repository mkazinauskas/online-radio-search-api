package com.modzo.ors.web.web.components.latest.searches;

import com.modzo.ors.web.web.utils.SeoText;

import java.util.List;

public class LatestSearchesData {

    private final List<Query> queries;

    public static class Query {

        private final String query;

        private final String type;

        private final String seoQuery;

        public Query(String query, String type) {
            this.query = query;
            this.type = type;
            this.seoQuery = SeoText.from(query);
        }

        public String getQuery() {
            return query;
        }

        public String getType() {
            return type;
        }

        public String getSeoQuery() {
            return seoQuery;
        }

        public boolean isGenreType() {
            return type.equalsIgnoreCase("genre");
        }

        public boolean isRadioStationType() {
            return type.equalsIgnoreCase("radiostation");
        }

        public boolean isSongType() {
            return type.equalsIgnoreCase("song");
        }
    }

    public LatestSearchesData(List<Query> queries) {
        this.queries = queries;
    }

    public List<Query> getQueries() {
        return queries;
    }
}
