package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.stations.domain.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public class SearchSongsByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchSongsByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

//        private final SongsRepository songsRepository;

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<Song> handle(SearchSongsByTitle command) {
//            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(searchInTitle(command))
//                    .withPageable(command.pageable)
//                    .withSort(sortByRelevance());
//
//            var result = songsRepository.search(searchQuery.build());
//
//            if (result.getNumberOfElements() > 0) {
//                lastSearchedQueryHandler.handle(new CreateSearchedQuery(command.title, SearchedQuery.Type.SONG));
//            }
//            return result;
            return null;
        }

//        private QueryBuilder searchInTitle(SearchSongsByTitle command) {
//            String title = "*" + command.title.replaceAll(" ", "* *") + "*";
//            QueryStringQueryBuilder partialSearch = queryStringQuery(title).field("title");
//
//            CommonTermsQueryBuilder findTitle = commonTermsQuery("title", command.title);
//            return boolQuery()
//                    .should(findTitle)
//                    .should(partialSearch);
//        }
//
//        private ScoreSortBuilder sortByRelevance() {
//            return SortBuilders.scoreSort();
//        }
    }
}