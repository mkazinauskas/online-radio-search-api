package com.modzo.ors.search.domain.commands;

import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.SongsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class SearchSongsByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchSongsByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {
        private final SongsRepository songsRepository;

        public Handler(SongsRepository songsRepository) {
            this.songsRepository = songsRepository;
        }

        public Page<SongDocument> handle(SearchSongsByTitle command) {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchInTitle(command))
                    .withPageable(command.pageable)
                    .withSort(sortByRelevance());
            return songsRepository.search(searchQuery.build());
        }

        private BoolQueryBuilder searchInTitle(SearchSongsByTitle command) {
            return boolQuery().should(queryStringQuery(command.title).field("title"));
        }

        private ScoreSortBuilder sortByRelevance() {
            return SortBuilders.scoreSort();
        }
    }
}