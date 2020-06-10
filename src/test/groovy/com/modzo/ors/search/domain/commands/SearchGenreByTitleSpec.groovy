package com.modzo.ors.search.domain.commands

import com.modzo.ors.search.domain.GenreDocument
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Unroll

class SearchGenreByTitleSpec extends IntegrationSpec {

    @Autowired
    SearchGenreByTitle.Handler testTarget

    @Unroll
    void 'should search for `#searchTerm` in `#title`'() {
        given:
            Genre savedGenre = testGenre.create("$title ${RandomStringUtils.randomAlphanumeric(5)}")
        when:
            Page<GenreDocument> result = testTarget.handle(new SearchGenreByTitle(searchTerm, Pageable.unpaged()))
        then:
            result.content.find { genre -> genre.id == savedGenre.id }
        where:
            title             | searchTerm
            'Rock & Pop'      | 'Rock'
            'Jazz & Electro'  | 'Electro'
            'Santropezo'      | 'Sant'
            'Barkendiki'      | 'diki'
            'Power deep dive' | 'Power dive'
    }
}
