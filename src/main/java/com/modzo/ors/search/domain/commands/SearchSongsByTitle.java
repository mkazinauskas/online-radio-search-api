package com.modzo.ors.search.domain.commands;

import com.modzo.ors.commons.SqlHelper;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
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

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        private final Songs songs;

        public Handler(CreateSearchedQuery.Handler lastSearchedQueryHandler, Songs songs) {
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
            this.songs = songs;
        }

        public Page<Song> handle(SearchSongsByTitle command) {
            var result = songs.findAllByTitleAndEnabledTrue(
                    SqlHelper.toILikeSearch(command.title),
                    command.pageable
            );

            if (result.getNumberOfElements() > 0) {
                lastSearchedQueryHandler.handle(new CreateSearchedQuery(command.title, SearchedQuery.Type.SONG));
            }
            return result;
        }

    }
}