package com.modzo.ors.stations.domain.radio.station.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class UpdateRadioStationSpec extends IntegrationSpec {

    @Autowired
    private UpdateRadioStation.Handler testTarget

    void 'should update radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create(testGenre.create())
        and:
            Genre genre = testGenre.create()
        and:
            UpdateRadioStation command = new UpdateRadioStation(
                    radioStation.id,
                    new UpdateRadioStation.DataBuilder()
                            .setTitle('new title')
                            .setWebsite('awesome website')
                            .setEnabled(true)
                            .setGenres([new UpdateRadioStation.Data.Genre(genre.id)] as Set)
                            .build()
            )
        when:
            testTarget.handle(command)
        then:
            RadioStation savedRadioStation = radioStations.findById(radioStation.id).get()
            savedRadioStation.title == command.data.title
            savedRadioStation.website == command.data.website
            savedRadioStation.enabled == command.data.enabled
            savedRadioStation.genres*.uniqueId.first() == genre.uniqueId
    }
}