package com.modzo.ors.last.searches.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQueries;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public class GetLastSearchQueries {

    private final Pageable pageable;

    public GetLastSearchQueries(Pageable pageable) {
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

        private final SearchedQueries searchedQueries;

        public Handler(SearchedQueries searchedQueries) {
            this.searchedQueries = searchedQueries;
        }

        public Page<SearchedQuery> handle(GetLastSearchQueries command) {
            return searchedQueries.findAll(command.pageable);
        }
    }
}
