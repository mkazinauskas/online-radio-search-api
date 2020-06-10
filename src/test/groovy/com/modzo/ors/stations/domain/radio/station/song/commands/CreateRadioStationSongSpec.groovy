package com.modzo.ors.stations.domain.radio.station.song.commands

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import java.time.ZonedDateTime

class CreateRadioStationSongSpec extends IntegrationSpec {

    @Autowired
    private CreateRadioStationSong.Handler testTarget

    void 'should create radio station song'() {
        given:
            Song song = testSong.create()
        and:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateRadioStationSong command = new CreateRadioStationSong(
                    song.id,
                    radioStation.id,
                    ZonedDateTime.now()
            )
        when:
            CreateRadioStationSong.Result result = testTarget.handle(command)
        then:
            RadioStationSong radioStationSong = radioStationSongs.findById(result.id).get()
            radioStationSong.songId == song.id
            radioStationSong.uniqueId.size() == 40
            radioStationSong.playedTime == command.playedTime
    }
}
