package com.mozdzo.ors.domain.radio.station.song.commands

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.events.RadioStationSongCreated
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.domain.radio.station.song.RadioStationSongs
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import java.time.ZonedDateTime

import static com.mozdzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_SONG_CREATED
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
