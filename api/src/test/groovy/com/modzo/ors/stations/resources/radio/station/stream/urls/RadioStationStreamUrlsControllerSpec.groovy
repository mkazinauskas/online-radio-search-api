package com.modzo.ors.stations.resources.radio.station.stream.urls

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class RadioStationStreamUrlsControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve radio station stream urls'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            StreamUrl streamUrl = testStreamUrl.create(stream.radioStationId, stream.id, StreamUrl.Type.INFO)
        and:
            String url = "/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls"
        when:
            ResponseEntity<RadioStationStreamUrlsModel> result = restTemplate.exchange(
                    "${url}?size=100&page=0",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    RadioStationStreamUrlsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                RadioStationStreamUrlModel resource = it.content.first()

                with(resource.content) {
                    it.id == streamUrl.id
                    it.uniqueId == streamUrl.uniqueId
                    it.created.toInstant() == streamUrl.created.toInstant()
                    it.url == streamUrl.url
                    it.type == streamUrl.type
                }

                resource.links.first().rel == SELF
                resource.links.first().href.endsWith("${url}/${streamUrl.id}")

                links.first().rel == SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve radio station stream url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            StreamUrl streamUrl = testStreamUrl.create(stream.radioStationId, stream.id, StreamUrl.Type.SONGS)
        and:
            String url = "/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls/${streamUrl.id}"
        when:
            ResponseEntity<RadioStationStreamUrlModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    RadioStationStreamUrlModel
            )
        then:
            result.statusCode == OK
        and:
            RadioStationStreamUrlModel resource = result.body
            with(result.body.content) {
                it.id == streamUrl.id
                it.uniqueId == streamUrl.uniqueId
                it.created.toInstant() == streamUrl.created.toInstant()
                it.url == streamUrl.url
                it.type == streamUrl.type
            }
        and:
            with(resource.links.first()) {
                it.rel == SELF
                it.href.endsWith(url)
            }
    }
}
