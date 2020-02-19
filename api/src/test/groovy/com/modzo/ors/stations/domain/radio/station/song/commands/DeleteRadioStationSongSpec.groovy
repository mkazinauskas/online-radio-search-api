package com.modzo.ors.stations.domain.radio.station.song.commands

import com.modzo.ors.stations.domain.events.Event
import com.modzo.ors.stations.domain.events.Events
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.stations.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.stations.domain.events.Event.Type.RADIO_STATION_SONG_DELETED
import static com.modzo.ors.stations.domain.events.RadioStationSongDeleted.Data
import static org.springframework.data.domain.Pageable.unpaged

class DeleteRadioStationSongSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStationSong.Handler testTarget

    @Autowired
    private RadioStationSongs radioStationSongs

    @Autowired
    private Events events

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
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_SONG_DELETED, unpaged())
        and:
            Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { Data data -> data.uniqueId == testRadioStationSong.uniqueId }
            event.radioStationUniqueId == testRadioStation.uniqueId
    }
}
