package com.modzo.ors.search.domain.commands;

import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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

        public Handler(RadioStationsRepository radioStationsRepository) {
            this.radioStationsRepository = radioStationsRepository;
        }

        public Page<RadioStationDocument> handle(SearchRadioStationByTitle command) {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchInTitle(command))
                    .withPageable(command.pageable)
                    .withSort(sortByRelevance());
            return radioStationsRepository.search(searchQuery.build());
        }

        private BoolQueryBuilder searchInTitle(SearchRadioStationByTitle command) {
            return boolQuery().should(queryStringQuery(command.title).field("title"));
        }

        private ScoreSortBuilder sortByRelevance() {
            return SortBuilders.scoreSort();
        }
    }
}