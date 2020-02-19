package com.modzo.ors.stations.domain.radio.station.song.commands

import com.modzo.ors.stations.domain.events.Event
import com.modzo.ors.stations.domain.events.Events
import com.modzo.ors.stations.domain.events.RadioStationSongCreated
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import java.time.ZonedDateTime

import static com.modzo.ors.stations.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.stations.domain.events.Event.Type.RADIO_STATION_SONG_CREATED
import static java.time.ZoneId.systemDefault
import static org.springframework.data.domain.Pageable.unpaged

class CreateRadioStationSongSpec extends IntegrationSpec {
    @Autowired
    private CreateRadioStationSong.Handler testTarget

    @Autowired
    private RadioStationSongs radioStationSongs

    @Autowired
    private Events events

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
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_SONG_CREATED, unpaged())
        and:
            RadioStationSongCreated.Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { RadioStationSongCreated.Data data -> data.uniqueId == radioStationSong.uniqueId }
            event.uniqueId == radioStationSong.uniqueId
            event.songUniqueId == song.uniqueId
            event.playedTime.withZoneSameInstant(systemDefault()) == radioStationSong.playedTime
    }
}
