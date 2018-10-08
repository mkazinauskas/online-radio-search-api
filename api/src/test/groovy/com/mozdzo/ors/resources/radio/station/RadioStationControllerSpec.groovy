package com.mozdzo.ors.resources.radio.station

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve radio stations'() {
        given:
            testRadioStation.create()
        and:
            String url = '/radio-stations'
        when:
            ResponseEntity<RadioStationsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content.first().radioStation.id > 0
                it.content.first().radioStation.title.size() > 0

                it.links.first().rel == REL_SELF
                it.links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            String url = "/radio-stations/${radioStation.id}"
        when:
            ResponseEntity<RadioStationResource> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.radioStation.id == radioStation.id
                it.radioStation.title == radioStation.title

                it.links.first().rel == REL_SELF
                it.links.first().href.endsWith(url)
            }
    }
}