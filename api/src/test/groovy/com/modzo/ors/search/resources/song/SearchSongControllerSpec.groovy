package com.modzo.ors.search.resources.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.search.resources.song.SongResultsResource
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchSongControllerSpec extends IntegrationSpec {

    void 'should find song'() {
        given:
            Song song = testSong.create()
        and:
            eventsProcessor.process()
        and:
            String url = '/search/song'
        when:
            ResponseEntity<SongResultsResource> result = restTemplate.exchange(
                    url + "?title=${song.title}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SongResultsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content.first().song.with {
                    uniqueId == song.uniqueId
                    title == song.title
                }
                it.links.first().with {
                    rel == REL_SELF
                    href.endsWith(url + '?title=')
                }
            }
    }
}