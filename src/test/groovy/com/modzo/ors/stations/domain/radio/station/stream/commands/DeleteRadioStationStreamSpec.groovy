package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class DeleteRadioStationStreamSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStationStream.Handler testTarget

    void 'should delete radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        expect:
            radioStationStreams.findById(stream.id).isPresent()
        when:
            DeleteRadioStationStream command = new DeleteRadioStationStream(
                    stream.radioStationId,
                    stream.id
            )
            testTarget.handle(command)
        then:
            radioStationStreams.findById(stream.id).isEmpty()
    }

}
