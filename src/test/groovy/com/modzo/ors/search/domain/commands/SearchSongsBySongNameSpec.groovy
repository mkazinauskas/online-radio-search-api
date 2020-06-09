package com.modzo.ors.search.domain.commands

import com.modzo.ors.search.domain.SongDocument
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

class SearchSongsBySongNameSpec extends IntegrationSpec {

    @Autowired
    SearchSongsByTitle.Handler testTarget

    @Unroll
    void 'should search for `#searchTerm` in song `#title`'() {
        given:
            Song savedSong = testSong.create("$title ${randomAlphanumeric(5)}")
        when:
            Page<SongDocument> result = testTarget.handle(
                    new SearchSongsByTitle(searchTerm, Pageable.unpaged())
            )
        then:
            result.content.find { song -> song.id == savedSong.id }
        where:
            title                   | searchTerm
            'Power Hit Radio'       | 'Power'
            'Be together ever'      | 'ever'
            'Stealmentezo'          | 'Steal'
            'Bebikinikini'          | 'kini'
            'Go deep into the trap' | 'Go trap'
    }
}
