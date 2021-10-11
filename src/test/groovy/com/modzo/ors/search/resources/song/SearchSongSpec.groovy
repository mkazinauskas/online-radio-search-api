package com.modzo.ors.search.resources.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import spock.lang.Unroll

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchSongSpec extends IntegrationSpec {

    @Unroll
    void 'should find song by title #songTitle'() {
        given:
            Song song = testSong.create(songTitle)
        and:
            String url = '/search/song'
        when:
            ResponseEntity<SearchSongResultsModel> result = restTemplate.exchange(
                    url + "?title=${query}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SearchSongResultsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                with(it.content.first()) {
                    it.id == song.id
                    it.uniqueId == song.uniqueId
                    it.title == song.title
                }
                with(it.links.first()) {
                    rel == SELF
                    String encodedQuery = query.replaceAll(' ', '%20')
                    href.endsWith("${url}?title=${encodedQuery}")
                }
            }
        where:
            songTitle                                              || query
            "${randomTitle()} draryrian ${randomTitle()}"          || 'draryrian'
            "${randomTitle()} partlg6hihltspl go ${randomTitle()}" || 'g6hihlts'

    }

    private static String randomTitle() {
        return RandomStringUtils.randomAlphanumeric(4)
    }
}