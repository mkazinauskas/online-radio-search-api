package com.modzo.ors.stations.domain.radio.station.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.RadioStations
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class DeleteRadioStationSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStation.Handler testTarget

    @Autowired
    private RadioStations radioStations

    void 'should delete radio station'() {
        given:
            RadioStation testRadioStation = testRadioStation.create()
        expect:
            radioStations.findById(testRadioStation.id).isPresent()
        when:
            DeleteRadioStation command = new DeleteRadioStation(
                    testRadioStation.id
            )
            testTarget.handle(command)
        then:
            !radioStations.findById(testRadioStation.id).isPresent()
    }
}
