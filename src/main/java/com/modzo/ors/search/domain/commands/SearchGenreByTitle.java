package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public class SearchGenreByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchGenreByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

        private final Genres genres;

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(Genres genres,
                       CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.genres = genres;
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<Genre> handle(SearchGenreByTitle command) {
//            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(searchInTitle(command))
//                    .withPageable(command.pageable)
//                    .withSort(sortByRelevance());
//
//            var result = genresRepository.search(searchQuery.build());
//
//            if (result.getNumberOfElements() > 0) {
//                lastSearchedQueryHandler.handle(
//                        new CreateSearchedQuery(command.title, SearchedQuery.Type.GENRE)
//                );
//            }
//            return result;
            return null;
        }

//        private QueryBuilder searchInTitle(SearchGenreByTitle command) {
//            String title = "*" + command.title.replaceAll(" ", "* *") + "*";
//            QueryStringQueryBuilder partialSearch = queryStringQuery(title).field("title");
//
//            CommonTermsQueryBuilder findTitle = commonTermsQuery("title", command.title);
//            return boolQuery()
//                    .should(partialSearch)
//                    .should(findTitle);
//        }
//
//        private ScoreSortBuilder sortByRelevance() {
//            return SortBuilders.scoreSort();
//        }
    }
}