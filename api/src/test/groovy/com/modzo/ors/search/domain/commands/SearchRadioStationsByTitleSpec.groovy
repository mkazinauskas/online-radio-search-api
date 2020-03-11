package com.modzo.ors.search.domain.commands

import com.modzo.ors.search.domain.RadioStationDocument
import com.modzo.ors.stations.resources.IntegrationSpec
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SearchRadioStationsByTitleSpec extends IntegrationSpec {

    @Autowired
    SearchRadioStationByTitle.Handler searchRadioStationByTitle

    void 'should find not disabled radio stations by title'() {
        given:
            String stationTitle = RandomStringUtils.randomAlphanumeric(5)
        and:
            RadioStationDocument enabledStation = testRadioStationDocument.create(stationTitle + ' enabled')
            testRadioStationDocument.create(stationTitle + ' disabled', false)
        when:
            Page<RadioStationDocument> radioStations = search(stationTitle)
        then:
            radioStations.size() == 1
            radioStationExist(radioStations, enabledStation)
    }

    private static boolean radioStationExist(Page<RadioStationDocument> radioStations, RadioStationDocument station) {
        radioStations.content.findAll { it.uniqueId == station.uniqueId }.size() == 1
    }

    private Page<RadioStationDocument> search(String query) {
        searchRadioStationByTitle.handle(
                new SearchRadioStationByTitle(query, Pageable.unpaged())
        )
    }
}
