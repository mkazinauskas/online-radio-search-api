package com.modzo.ors.stations.domain.radio.station.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class CreateRadioStationSpec extends IntegrationSpec {

    @Autowired
    private CreateRadioStation.Handler testTarget

    void 'should create radio station'() {
        given:
            CreateRadioStation command = new CreateRadioStation(
                    RandomStringUtils.randomAlphanumeric(10)
            )
        when:
            CreateRadioStation.Result result = testTarget.handle(command)
        then:
            RadioStation savedRadioStation = radioStations.findById(result.id).get()
            savedRadioStation.title == command.title
            savedRadioStation.id == result.id
            savedRadioStation.uniqueId.size() == 20
            savedRadioStation.genres.empty
    }
}
