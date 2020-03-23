package com.modzo.ors.helpers

import com.modzo.ors.setup.WiremockConfiguration
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStreamUrl
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.transaction.Transactional

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
@CompileStatic
class TestRadioStationStreamUrl {

    @Autowired
    private WiremockConfiguration wiremockConfiguration

    @Autowired
    private TestRadioStationStream testRadioStationStream

    @Autowired
    private CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl

    @Autowired
    private StreamUrls streamUrls

    @Transactional
    StreamUrl create(long radioStationId, long streamId, StreamUrl.Type type = StreamUrl.Type.INFO) {
        CreateRadioStationStreamUrl request = new CreateRadioStationStreamUrl(
                radioStationId,
                streamId,
                type,
                randomStreamUrl()
        )

        long savedStreamId = createRadioStationStreamUrl.handle(request).id

        return streamUrls.findById(savedStreamId).get()
    }

    StreamUrl create(StreamUrl.Type type = StreamUrl.Type.INFO) {
        RadioStationStream stream = testRadioStationStream.create()
        return create(stream.radioStationId, stream.id, type)
    }

    String randomStreamUrl() {
        return "http://localhost:${wiremockConfiguration.port}/${randomAlphanumeric(50)}"
    }
}
