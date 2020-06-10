package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class DeleteRadioStationStreamUrlSpec extends IntegrationSpec {

    @Autowired
    DeleteRadioStationStreamUrl.Handler deleteRadioStationStreamUrl

    void 'should delete radio station stream url'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create()
        when:
            deleteRadioStationStreamUrl.handle(
                    new DeleteRadioStationStreamUrl(
                            streamUrl.stream.radioStationId, streamUrl.stream.id, streamUrl.id
                    )
            )
        then:
            streamUrls.findById(streamUrl.id).isEmpty()
    }

}
