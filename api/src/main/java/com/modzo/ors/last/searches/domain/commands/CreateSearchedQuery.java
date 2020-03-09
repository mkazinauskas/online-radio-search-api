package com.modzo.ors.last.searches.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQueries;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.stations.domain.DomainException;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

public class CreateSearchedQuery {

    private final String query;

    private final SearchedQuery.Type type;

    public CreateSearchedQuery(String query, SearchedQuery.Type type) {
        this.query = query;
        this.type = type;
    }

    @Component
    public static class Handler {

        private final SearchedQueries searchedQueries;

        private final Validator validator;

        public Handler(SearchedQueries searchedQueries,
                       Validator validator) {
            this.searchedQueries = searchedQueries;
            this.validator = validator;
        }

        public SearchedQuery handle(CreateSearchedQuery command) {
            validator.validate(command);
            List<SearchedQuery> oldSearchesToDelete = searchedQueries.findAllByQueryAndType(command.query, command.type);
            searchedQueries.deleteAll(oldSearchesToDelete);

            return searchedQueries.save(new SearchedQuery(command.query, command.type));
        }
    }

    @Component
    private static class Validator {

        void validate(CreateSearchedQuery command) {
            if (StringUtil.isBlank(command.query)) {
                throw new DomainException("SEARCH_QUERY_NOT_BLANK", "Searched query was blank");
            }
        }
    }
}
