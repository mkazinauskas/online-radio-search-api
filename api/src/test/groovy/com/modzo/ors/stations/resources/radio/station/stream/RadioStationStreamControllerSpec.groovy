package com.modzo.ors.stations.resources.radio.station.stream

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
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
            ResponseEntity<RadioStationStreamsModel> result = restTemplate.exchange(
                    "${url}?size=100&page=0",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationStreamsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                RadioStationStreamModel resource = it.content.first()

                with(resource.content) {
                    it.id == radioStationStream.id
                    it.uniqueId == radioStationStream.uniqueId
                    it.created == radioStationStream.created
                    it.url == radioStationStream.url
                    it.bitRate == radioStationStream.bitRate
                    it.type == radioStationStream.type.map { it.name() }.orElse(null)
                }

                resource.links.first().rel == SELF
                resource.links.first().href.endsWith("${url}/${radioStationStream.id}")

                links.first().rel == SELF
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
            ResponseEntity<RadioStationStreamModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationStreamModel
            )
        then:
            result.statusCode == OK
        and:
            RadioStationStreamModel resource = result.body
            with(result.body.content) {
                it.id == stream.id
                it.uniqueId == stream.uniqueId
                it.url == stream.url
                it.bitRate == stream.bitRate
                it.type == stream.type.map { it.name() }.orElse(null)
            }
        and:
            with(resource.links.first()) {
                it.rel == SELF
                it.href.endsWith(url)
            }
    }
}