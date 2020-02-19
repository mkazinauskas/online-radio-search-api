package com.modzo.ors.helpers

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.commands.CreateRadioStation
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStation {

    @Autowired
    private CreateRadioStation.Handler createRadioStationHandler

    @Autowired
    private GetRadioStation.Handler getRadioStationHandler

    RadioStation create() {
        CreateRadioStation createRadioStation = new CreateRadioStation(randomWebsite())
        long newStationId = createRadioStationHandler.handle(createRadioStation).id
        return getRadioStationHandler.handle(new GetRadioStation(newStationId))
    }

    static String randomWebsite() {
        randomAlphanumeric(100)
    }

    static String randomTitle() {
        randomAlphanumeric(40)
    }
}
