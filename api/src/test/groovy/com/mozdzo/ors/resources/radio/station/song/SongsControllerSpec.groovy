package com.mozdzo.ors.resources.radio.station.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SongsControllerSpec extends IntegrationSpec {

    def 'anyone should retrieve radio station songs'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs"
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
                SongResource resource = it.content.first() as SongResource

                resource.song.id == song.id
                resource.song.title == song.title
                resource.song.playingTime == song.playingTime

                resource.links.first().rel == REL_SELF
                resource.links.first().href.endsWith("${url}/${song.id}")

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    def 'anyone should retrieve radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs/${song.id}"
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
                it.song.id == song.id
                it.song.title == song.title
                it.song.playingTime == song.playingTime

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}