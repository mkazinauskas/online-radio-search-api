package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.GenresRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class SearchGenreByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchGenreByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

        private final GenresRepository genresRepository;

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(GenresRepository genresRepository,
                       CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.genresRepository = genresRepository;
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<GenreDocument> handle(SearchGenreByTitle command) {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchInTitle(command))
                    .withPageable(command.pageable)
                    .withSort(sortByRelevance());

            var result = genresRepository.search(searchQuery.build());

            if (result.getNumberOfElements() > 0) {
                lastSearchedQueryHandler.handle(
                        new CreateSearchedQuery(command.title, SearchedQuery.Type.GENRE)
                );
            }
            return result;
        }

        private BoolQueryBuilder searchInTitle(SearchGenreByTitle command) {
            return boolQuery().should(queryStringQuery(command.title).field("title"));
        }

        private ScoreSortBuilder sortByRelevance() {
            return SortBuilders.scoreSort();
        }
    }
}