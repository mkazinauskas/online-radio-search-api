package com.modzo.ors.search.domain.commands


import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

class SearchRadioStationsByTitleSpec extends IntegrationSpec {

    @Autowired
    SearchRadioStationByTitle.Handler testTarget

    void 'should find not disabled radio stations by title'() {
//        given:
//            String stationTitle = randomAlphanumeric(5)
//        and:
//            RadioStationDocument enabledStation = testRadioStationDocument.create(stationTitle + ' enabled')
//            testRadioStationDocument.create(stationTitle + ' disabled', false)
//        when:
//            Page<RadioStationDocument> radioStations = search(stationTitle)
//        then:
//            radioStations.size() == 1
//            radioStationExist(radioStations, enabledStation)
    }

    @Unroll
    void 'should search for `#searchTerm` in radio station title `#title`'() {
//        given:
//            RadioStation radioStation = testRadioStation.create("$title ${randomAlphanumeric(5)}")
//        when:
//            Page<RadioStationDocument> result = testTarget.handle(
//                    new SearchRadioStationByTitle(searchTerm, Pageable.unpaged())
//            )
//        then:
//            result.content.find { station -> station.id == radioStation.id }
//        where:
//            title                   | searchTerm
//            'Power Hit Radio'       | 'Power'
//            'Be together ever'      | 'ever'
//            'Stealmentezo'          | 'Steal'
//            'Bebikinikini'          | 'kini'
//            'Go deep into the trap' | 'Go trap'
    }

//    private static boolean radioStationExist(Page<RadioStationDocument> radioStations, RadioStationDocument station) {
//        radioStations.content.findAll { it.uniqueId == station.uniqueId }.size() == 1
//    }
//
//    private Page<RadioStationDocument> search(String query) {
//        testTarget.handle(
//                new SearchRadioStationByTitle(query, Pageable.unpaged())
//        )
//    }
}
