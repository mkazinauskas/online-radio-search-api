package com.mozdzo.ors.resources.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SongControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve songs'() {
        given:
            Song song = testSong.create()
        and:
            String url = '/songs'
        when:
            ResponseEntity<SongsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SongsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as SongsResource) {
                SongResource resource = it.content.find { it.getRadioStationSong.id == song.id } as SongResource

                resource.song.title == song.title

                resource.links.first().rel == REL_SELF
                resource.links.first().href.endsWith("${url}/${song.id}")

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve song'() {
        given:
            Song song = testSong.create()
        and:
            String url = "/songs/${song.id}"
        when:
            ResponseEntity<SongResource> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    SongResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as SongResource) {
                it.getRadioStationSong.id == song.id
                it.getRadioStationSong.title == song.title

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}
