package com.modzo.ors.helpers

import com.modzo.ors.setup.WiremockConfiguration
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStreamUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStationStreamUrl {

    @Autowired
    private WiremockConfiguration wiremockConfiguration

    @Autowired
    private TestRadioStationStream testRadioStationStream

    @Autowired
    CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl

    StreamUrl create(long radioStationId, long streamId, StreamUrl.Type type = StreamUrl.Type.INFO) {
        CreateRadioStationStreamUrl request = new CreateRadioStationStreamUrl(
                radioStationId,
                streamId,
                type,
                randomStreamUrl()
        )

        return createRadioStationStreamUrl.handle(request)
    }

    StreamUrl create(StreamUrl.Type type = StreamUrl.Type.INFO) {
        RadioStationStream stream = testRadioStationStream.create()
        return create(stream.radioStationId, stream.id, type)
    }

    String randomStreamUrl() {
        return "http://localhost:${wiremockConfiguration.port}/${randomAlphanumeric(50)}"
    }
}
