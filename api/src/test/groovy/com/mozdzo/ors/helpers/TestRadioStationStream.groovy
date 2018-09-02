package com.mozdzo.ors.helpers

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.CreateRadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStationStream {

    @Autowired
    private CreateRadioStationStream.Handler createRadioStationHandler

    @Autowired
    private GetRadioStationStream.Handler getRadioStationHandler

    RadioStationStream create(long radioStationId) {
        CreateRadioStationStream createRadioStation = new CreateRadioStationStream(
                radioStationId,
                randomAlphanumeric(100)
        )
        long newStationId = createRadioStationHandler.handle(createRadioStation).id

        return getRadioStationHandler.handle(new GetRadioStationStream(radioStationId, newStationId))
    }
}
