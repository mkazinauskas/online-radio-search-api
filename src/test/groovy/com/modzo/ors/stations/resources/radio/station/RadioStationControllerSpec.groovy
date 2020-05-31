package com.modzo.ors.stations.resources.radio.station

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve radio stations'() {
        given:
            testRadioStation.create()
        and:
            String url = '/radio-stations'
        when:
            ResponseEntity<RadioStationsModel> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                with(it.content.first()) {
                    with(it.content) {
                        it.id
                        it.uniqueId
                        it.created
                        it.title.size() > 0
                    }
                    with(it.links.first()) {
                        rel == SELF
                        href.contains(url)
                    }
                }
                it.links.first().with {
                    rel == SELF
                    href.endsWith(url)
                }
            }
    }

    void 'anyone should retrieve filtered radio stations'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            String filter = toRequestParams([
                    'id'      : radioStation.id,
                    'uniqueId': radioStation.uniqueId,
                    'enabled' : radioStation.enabled,
                    'title'   : radioStation.title
            ])
        when:
            ResponseEntity<RadioStationsModel> result = restTemplate.exchange(
                    "/radio-stations?${filter}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.getMetadata().totalElements == 1
                with(it.content.first()) {
                    with(it.content) {
                        it.id == radioStation.id
                        it.uniqueId == radioStation.uniqueId
                        it.enabled == radioStation.enabled
                        it.title == radioStation.title
                    }
                }
            }
    }

    void 'anyone should retrieve filtered radio stations by song id'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationSong radioStationSong = testRadioStationSong.create(radioStation.id)
        and:
            String filter = toRequestParams([
                    'songId': radioStationSong.songId,
            ])
        when:
            ResponseEntity<RadioStationsModel> result = restTemplate.exchange(
                    "/radio-stations?${filter}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.getMetadata().totalElements == 1
                with(it.content.first()) {
                    with(it.content) {
                        it.id == radioStation.id
                        it.uniqueId == radioStation.uniqueId
                        it.enabled == radioStation.enabled
                        it.title == radioStation.title
                    }
                }
            }
    }

    void 'anyone should retrieve filtered radio stations by genre id'() {
        given:
            Genre genre = testGenre.create()
        and:
            RadioStation radioStation = testRadioStation.create(genre)
        and:
            String filter = toRequestParams([
                    'genreId': genre.id,
            ])
        when:
            ResponseEntity<RadioStationsModel> result = restTemplate.exchange(
                    "/radio-stations?${filter}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.getMetadata().totalElements == 1
                with(it.content.first()) {
                    with(it.content) {
                        it.id == radioStation.id
                        it.uniqueId == radioStation.uniqueId
                        it.enabled == radioStation.enabled
                        it.title == radioStation.title
                    }
                }
            }
    }

    void 'anyone should retrieve radio station'() {
        given:
            Genre genre = testGenre.create()
            RadioStation radioStation = testRadioStation.create(genre)
        and:
            String url = "/radio-stations/${radioStation.id}"
        when:
            ResponseEntity<RadioStationModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content.with {
                    it.id == radioStation.id
                    it.uniqueId == radioStation.uniqueId
                    it.created == radioStation.created
                    it.title == radioStation.title
                    it.enabled == radioStation.enabled
                    it.genres.size() == 1
                    with(it.genres.first()) {
                        it.id == genre.id
                        it.uniqueId == genre.uniqueId
                        it.title == genre.title
                    }
                }

                with(it.links.first()) {
                    it.rel == SELF
                    it.href.endsWith(url)
                }
            }
    }

    private static String toRequestParams(Map<String, Object> params) {
        return params
                .collect { entry -> "${entry.key}=${entry.value}" }
                .join('&')
    }
}