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
                    HttpEntityBuilder.quick(),
                    RadioStationSongsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as RadioStationSongsResource) {
                RadioStationSongResource resource = it.content
                        .find { RadioStationSongResource resource -> resource.radioStationSong.id == radioStationSong.id } as RadioStationSongResource

                resource.radioStationSong.id == radioStationSong.id
                resource.radioStationSong.playingTime.withZoneSameInstant(systemDefault()) == radioStationSong.playingTime

                resource.links.first().rel == REL_SELF
                resource.links.first().href.endsWith("${url}/${radioStationSong.id}")

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
                it.radioStationSong.playingTime.withZoneSameInstant(systemDefault()) == radioStationSong.playingTime

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}