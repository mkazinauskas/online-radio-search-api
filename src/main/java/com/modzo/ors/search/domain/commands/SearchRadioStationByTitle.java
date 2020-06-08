package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.commonTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class SearchRadioStationByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchRadioStationByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

        private final RadioStationsRepository radioStationsRepository;

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(RadioStationsRepository radioStationsRepository,
                       CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.radioStationsRepository = radioStationsRepository;
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<RadioStationDocument> handle(SearchRadioStationByTitle command) {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchInTitle(command))
                    .withPageable(command.pageable)
                    .withSort(sortByRelevance());

            var result = radioStationsRepository.search(searchQuery.build());

            if (result.getNumberOfElements() > 0) {
                lastSearchedQueryHandler.handle(
                        new CreateSearchedQuery(command.title, SearchedQuery.Type.RADIO_STATION)
                );
            }
            return result;
        }

        private QueryBuilder searchInTitle(SearchRadioStationByTitle command) {
            String title = "*" + command.title.replaceAll(" ", "* *") + "*";
            QueryStringQueryBuilder partialSearch = queryStringQuery(title).field("title");

            CommonTermsQueryBuilder findTitle = commonTermsQuery("title", command.title);

            TermQueryBuilder disabledStation = termQuery("enabled", false);
            return boolQuery()
                    .should(findTitle)
                    .should(partialSearch)
                    .mustNot(disabledStation);
        }

        private ScoreSortBuilder sortByRelevance() {
            return SortBuilders.scoreSort();
        }
    }
}