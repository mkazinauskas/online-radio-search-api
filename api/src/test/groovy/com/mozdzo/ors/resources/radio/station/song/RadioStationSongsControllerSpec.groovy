package com.mozdzo.ors.resources.radio.station.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static java.time.ZoneId.systemDefault
import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationSongsControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve radio station songs'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs"
        when:
            ResponseEntity<RadioStationSongsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationSongsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as RadioStationSongsResource) {
                RadioStationSongResource resource = it.content.first() as RadioStationSongResource

                resource.song.id == song.id
                resource.song.title == song.title
                resource.song.playingTime.withZoneSameInstant(systemDefault()) == song.playingTime

                resource.links.first().rel == REL_SELF
                resource.links.first().href.endsWith("${url}/${song.id}")

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs/${song.id}"
        when:
            ResponseEntity<RadioStationSongResource> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as RadioStationSongResource) {
                it.setSongId.id == song.id
                it.setSongId.title == song.title
                it.setSongId.playingTime.withZoneSameInstant(systemDefault()) == song.playingTime

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}