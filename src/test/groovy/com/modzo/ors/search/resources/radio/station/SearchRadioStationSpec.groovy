package com.modzo.ors.search.resources.radio.station

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import spock.lang.Unroll

import java.nio.charset.Charset

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchRadioStationSpec extends IntegrationSpec {

    @Unroll
    void 'should find radio station `#radioStationTitle` by query `#query`'() {
        given:
            RadioStation radioStation = testRadioStation.create(radioStationTitle)
        and:
            String url = '/search/radio-station'
        when:
            ResponseEntity<SearchRadioStationResultsModel> result = restTemplate.exchange(
                    url + "?title=${query}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SearchRadioStationResultsModel
            )
        then:
            result.statusCode == OK
        and:
            result.body
            with(result.body) {
                with(it.content.first()) {
                    it.id == radioStation.id
                    it.uniqueId == radioStation.uniqueId
                    it.title == radioStation.title
                }
                with(it.links.first()) {
                    rel == SELF
                    String encodedQuery = query.replaceAll(' ', '%20')
                    href.endsWith("${url}?title=${encodedQuery}")
                }
            }
        where:
            radioStationTitle                                                  | query
            randomTitle()                                                      | radioStationTitle
            "${randomTitle()} trixyblaze ${randomTitle()}"                     | 'trixyblaze'
            "${randomTitle()} partializertrancoder ${randomTitle()}"           | 'partializer'
            "${randomTitle()} partializertrancoder blazinger ${randomTitle()}" | 'partializertrancoder blazinger'
    }

    private static String randomTitle() {
        return RandomStringUtils.randomAlphanumeric(4)
    }
}