package com.modzo.ors.helpers

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream
import com.modzo.ors.setup.WiremockConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStationStream {

    @Autowired
    private CreateRadioStationStream.Handler createRadioStationHandler

    @Autowired
    private GetRadioStationStream.Handler getRadioStationHandler

    @Autowired
    private TestRadioStation testRadioStation

    @Autowired
    private WiremockConfiguration wiremockConfiguration

    RadioStationStream create(long radioStationId) {
        CreateRadioStationStream createRadioStation = new CreateRadioStationStream(
                radioStationId,
                randomStreamUrl()
        )
        long newStationId = createRadioStationHandler.handle(createRadioStation).id

        return getRadioStationHandler.handle(new GetRadioStationStream(radioStationId, newStationId))
    }

    RadioStationStream create() {
        RadioStation radioStation = testRadioStation.create()
        return create(radioStation.id)
    }

    String randomStreamUrl() {
        return "http://localhost:${wiremockConfiguration.httpPort}/${randomAlphanumeric(50)}"
    }
}
