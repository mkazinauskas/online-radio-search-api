package com.mozdzo.ors.searches.domain.commands;

import com.mozdzo.ors.searches.domain.SearchedQueries;
import com.mozdzo.ors.searches.domain.SearchedQuery;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

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
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchAllQuery())
                    .withPageable(command.pageable);

            searchQuery.withSort(
                    SortBuilders.fieldSort("id")
                            .unmappedType("long")
                            .order(SortOrder.DESC)
            );
            return searchedQueries.search(searchQuery.build());
        }
    }
}
