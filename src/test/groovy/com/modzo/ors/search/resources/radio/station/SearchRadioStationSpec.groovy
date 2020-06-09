package com.modzo.ors.search.resources.radio.station

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchRadioStationSpec extends IntegrationSpec {

    void 'should find radio station by title'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            String url = '/search/radio-station'
        when:
            ResponseEntity<SearchRadioStationResultsModel> result = restTemplate.exchange(
                    url + "?title=${radioStation.title}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SearchRadioStationResultsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                with(it.content.first()) {
                    it.id == radioStation.id
                    it.uniqueId == radioStation.uniqueId
                    it.title == radioStation.title
                }
                with(it.links.first()) {
                    rel == SELF
                    href.endsWith("${url}?title=${radioStation.title}")
                }
            }
    }
}