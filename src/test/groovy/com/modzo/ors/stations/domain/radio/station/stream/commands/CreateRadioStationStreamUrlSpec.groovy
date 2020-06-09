package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.helpers.FakeUrl
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class CreateRadioStationStreamUrlSpec extends IntegrationSpec {

    @Autowired
    CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl

    void 'should add radio station stream url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            CreateRadioStationStreamUrl request = new CreateRadioStationStreamUrl(
                    stream.radioStationId,
                    stream.id,
                    StreamUrl.Type.INFO,
                    FakeUrl.create()
            )
        when:
            createRadioStationStreamUrl.handle(request)
        then:
            RadioStationStream savedStream = radioStationStreams
                    .findByRadioStation_IdAndId(stream.radioStationId, stream.id)
                    .get()

            savedStream.urls.size() == 1
            StreamUrl savedUrl = savedStream.urls.get(StreamUrl.Type.INFO)
            savedUrl.url == request.url
    }

}
