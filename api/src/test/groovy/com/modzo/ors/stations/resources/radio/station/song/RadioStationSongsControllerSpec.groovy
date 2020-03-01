package com.modzo.ors.stations.resources.radio.station.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static java.time.ZoneId.systemDefault
import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationSongsControllerSpec extends IntegrationSpec {

    void 'should not fail to retrieve radio station songs, when none exist'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            String url = "/radio-stations/${radioStation.id}/songs"
        when:
            ResponseEntity<RadioStationSongsModel> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content.isEmpty()
                with(links.first()) {
                    rel == SELF
                    href.endsWith(url)
                }
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
            ResponseEntity<RadioStationSongsModel> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                RadioStationSongModel model = it.content
                        .find { item -> item.content.id == radioStationSong.id }
                with(model.content) {
                    it.id == radioStationSong.id
                    it.playedTime.withZoneSameInstant(systemDefault()) == radioStationSong.playedTime
                }

                with(model.links.first()) {
                    it.rel == SELF
                    it.href.endsWith("${url}/${radioStationSong.id}")
                }

                links.first().rel == SELF
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
            ResponseEntity<RadioStationSongModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationSongModel
            )
        then:
            result.statusCode == OK
        and:
            RadioStationSongModel model = result.body
            with(model.content) {
                it.id == radioStationSong.id
                it.songId == radioStationSong.songId
                it.playedTime.withZoneSameInstant(systemDefault()) == radioStationSong.playedTime
            }
            with(model.links.first()) {
                it.rel == SELF
                it.href.endsWith(url)
            }
    }
}