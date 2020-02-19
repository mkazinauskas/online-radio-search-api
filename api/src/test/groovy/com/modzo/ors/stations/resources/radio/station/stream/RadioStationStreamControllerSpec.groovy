package com.modzo.ors.stations.resources.radio.station.stream

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.Link.REL_SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationStreamControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve radio station streams'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream radioStationStream = testRadioStationStream.create(radioStation.id)
        and:
            String url = "/radio-stations/${radioStation.id}/streams"
        when:
            ResponseEntity<RadioStationStreamsResource> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationStreamsResource
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                RadioStationStreamResource resource = it.content.first() as RadioStationStreamResource

                resource.radioStationStream.id == radioStationStream.id
                resource.radioStationStream.url == radioStationStream.url

                resource.links.first().rel == REL_SELF
                resource.links.first().href.endsWith("${url}/${radioStationStream.id}")

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station stream'() {
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
            with(result.body as RadioStationStreamResource) {
                it.radioStationStream.id == stream.id
                it.radioStationStream.url == stream.url

                links.first().rel == REL_SELF
                links.first().href.endsWith(url)
            }
    }
}