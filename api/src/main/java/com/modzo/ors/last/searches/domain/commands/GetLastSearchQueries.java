package com.modzo.ors.last.searches.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQueries;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import org.elasticsearch.index.IndexNotFoundException;
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
                    SortBuilders.fieldSort("created")
                            .unmappedType("date")
                            .order(SortOrder.DESC)
            );
            try {
                return searchedQueries.search(searchQuery.build());
            } catch (IndexNotFoundException exception) {
                return Page.empty();
            }
        }
    }
}
