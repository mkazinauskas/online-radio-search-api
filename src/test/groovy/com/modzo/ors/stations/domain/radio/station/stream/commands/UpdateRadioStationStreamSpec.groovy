package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import static com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream.Type.ACC

class UpdateRadioStationStreamSpec extends IntegrationSpec {

    @Autowired
    private UpdateRadioStationStream.Handler testTarget

    void 'should create radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            UpdateRadioStationStream command = new UpdateRadioStationStream(
                    radioStation.id, stream.id,
                    new UpdateRadioStationStream.DataBuilder()
                            .setUrl('new test url')
                            .setBitRate(993)
                            .setType(ACC)
                            .setWorking(true)
                            .build()
            )
        when:
            testTarget.handle(command)
        then:
            RadioStationStream savedStream = radioStationStreams
                    .findByRadioStation_IdAndId(radioStation.id, stream.id)
                    .get()
            savedStream.url == command.data.url
            savedStream.type.get() == command.data.type
            savedStream.bitRate == command.data.bitRate
    }
}
