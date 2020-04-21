package com.modzo.ors.search

import com.modzo.ors.search.domain.RadioStationDocument
import com.modzo.ors.search.domain.RadioStationsRepository
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

@Component
class TestRadioStationDocument {

    private final RadioStationsRepository radioStationsRepository

    TestRadioStationDocument(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository
    }

    RadioStationDocument create(String title = randomTitle(), boolean enabled = true) {
        RadioStationDocument radioStationDocument = new RadioStationDocument(
                randomNumeric(10) as long,
                randomAlphanumeric(100),
                title,
                enabled
        )
        radioStationsRepository.save(radioStationDocument)
    }

    private static String randomTitle() {
        randomAlphanumeric(100)
    }
}
