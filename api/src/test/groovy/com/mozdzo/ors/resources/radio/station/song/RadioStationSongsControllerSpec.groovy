package com.mozdzo.ors.resources.radio.station.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static java.time.ZoneId.systemDefault
import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationSongsControllerSpec extends IntegrationSpec {

    void 'should not fail to retrieve radio station songs, when none exist'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            String url = "/radio-stations/${radioStation.id}/songs"
        when:
            ResponseEntity<RadioStationSongsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as RadioStationSongsResource) {
                it.content.isEmpty()

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station songs'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationSong radioStationSong = testRadioStationSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs"
        when:
            ResponseEntity<RadioStationSongsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as RadioStationSongsResource) {
                RadioStationSongResource song = it.content
                        .find { item -> item.radioStationSong.id == radioStationSong.id } as RadioStationSongResource

                song.radioStationSong.id == radioStationSong.id
                song.radioStationSong.playingTime.withZoneSameInstant(systemDefault()) == radioStationSong.playedTime

                song.links.first().rel == REL_SELF
                song.links.first().href.endsWith("${url}/${radioStationSong.id}")

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station song'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationSong radioStationSong = testRadioStationSong.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/songs/${radioStationSong.id}"
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
                it.radioStationSong.id == radioStationSong.id
                it.radioStationSong.songId == radioStationSong.songId
                it.radioStationSong.setPlayedTime.withZoneSameInstant(systemDefault()) == radioStationSong.playedTime

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}