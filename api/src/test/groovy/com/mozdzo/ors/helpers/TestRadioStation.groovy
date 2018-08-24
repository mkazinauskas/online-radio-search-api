package com.mozdzo.ors.helpers

import com.mozdzo.ors.domain.station.RadioStation
import com.mozdzo.ors.domain.station.commands.CreateRadioStation
import com.mozdzo.ors.domain.station.commands.GetRadioStation
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStation {

    @Autowired
    CreateRadioStation.Handler createRadioStationHandler

    @Autowired
    GetRadioStation.Handler getRadioStationHandler

    RadioStation create() {
        CreateRadioStation createRadioStation = new CreateRadioStation(randomAlphanumeric(100))
        long newStationId = createRadioStationHandler.handle(createRadioStation).id
        return getRadioStationHandler.handle(new GetRadioStation(newStationId))
    }
}
