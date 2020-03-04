package com.modzo.ors.search.resources.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchSongControllerSpec extends IntegrationSpec {

    void 'should find song by title'() {
        given:
            Song song = testSong.create()
        and:
            eventsProcessor.process()
        and:
            String url = '/search/song'
        when:
            ResponseEntity<SearchSongResultsModel> result = restTemplate.exchange(
                    url + "?title=${song.title}",
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
                    href.endsWith("${url}?title=${song.title}")
                }
            }
    }
}