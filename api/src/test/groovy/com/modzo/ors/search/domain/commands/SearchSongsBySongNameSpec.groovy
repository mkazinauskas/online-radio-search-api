package com.modzo.ors.search.domain.commands

import com.modzo.ors.search.domain.SongDocument
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SearchSongsBySongNameSpec extends IntegrationSpec {

    @Autowired
    SearchSongsByTitle.Handler searchSongHandler

    void 'should find song by title'() {
        given:
            SongDocument firstSong = testSongDocument.create('Jordana - rampapa')
            SongDocument secondSong = testSongDocument.create('Jirdana - urarampapa')
        when:
            Page<SongDocument> songs = search('Jordana')
        then:
            songs.size() == 1
            songExists(songs, firstSong)
        when:
            songs = search('urarampapa')
        then:
            songs.size() == 1
            songExists(songs, secondSong)
    }

    private static boolean songExists(Page<SongDocument> songs, SongDocument songDocument) {
        songs.content.findAll { it.uniqueId == songDocument.uniqueId }.size() == 1
    }

    private Page<SongDocument> search(String query) {
        searchSongHandler.handle(
                new SearchSongsByTitle(query, Pageable.unpaged())
        )
    }
}
