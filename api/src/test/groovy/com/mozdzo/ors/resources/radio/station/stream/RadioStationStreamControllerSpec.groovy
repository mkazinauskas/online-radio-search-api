package com.mozdzo.ors.resources.radio.station.stream

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.resources.IntegrationSpec
import com.mozdzo.ors.resources.radio.station.RadioStationResource
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationStreamControllerSpec extends IntegrationSpec {

    def 'anyone should retrieve radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/streams/${stream.id}"
        when:
            ResponseEntity<RadioStationStreamResource> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationStreamResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                radioStationStream.id == stream.id
                radioStationStream.url == stream.url

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}