package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.SongsRepository;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.commonTermsQuery;
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

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(SongsRepository songsRepository,
                       CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.songsRepository = songsRepository;
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<SongDocument> handle(SearchSongsByTitle command) {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchInTitle(command))
                    .withPageable(command.pageable)
                    .withSort(sortByRelevance());

            var result = songsRepository.search(searchQuery.build());

            if (result.getNumberOfElements() > 0) {
                lastSearchedQueryHandler.handle(new CreateSearchedQuery(command.title, SearchedQuery.Type.SONG));
            }
            return result;
        }

        private QueryBuilder searchInTitle(SearchSongsByTitle command) {
            String title = "*" + command.title.replaceAll(" ", "* *") + "*";
            QueryStringQueryBuilder partialSearch = queryStringQuery(title).field("title");

            CommonTermsQueryBuilder findTitle = commonTermsQuery("title", command.title);
            return boolQuery()
                    .should(findTitle)
                    .should(partialSearch);
        }

        private ScoreSortBuilder sortByRelevance() {
            return SortBuilders.scoreSort();
        }
    }
}