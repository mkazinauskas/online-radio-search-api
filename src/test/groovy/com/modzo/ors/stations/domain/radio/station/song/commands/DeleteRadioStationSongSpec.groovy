package com.modzo.ors.stations.domain.radio.station.song.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class DeleteRadioStationSongSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStationSong.Handler testTarget

    void 'should delete radio station song'() {
        given:
            RadioStation testRadioStation = testRadioStation.create()
            RadioStationSong testRadioStationSong = testRadioStationSong.create(testRadioStation.id)
        expect:
            radioStationSongs.findById(testRadioStationSong.id).isPresent()
        when:
            DeleteRadioStationSong command = new DeleteRadioStationSong(
                    testRadioStationSong.radioStationId,
                    testRadioStationSong.id
            )
            testTarget.handle(command)
        then:
            radioStationSongs.findById(testRadioStationSong.id).isEmpty()
    }
}
