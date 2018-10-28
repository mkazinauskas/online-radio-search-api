package com.mozdzo.ors.search.reader

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import com.mozdzo.ors.search.*
import com.mozdzo.ors.search.reader.parser.EventsParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.mozdzo.ors.domain.events.Event.Type.*
import static org.springframework.data.domain.Pageable.unpaged

class EventsParserSpec extends IntegrationSpec {

    @Autowired
    private EventsParser eventsParser

    @Autowired
    private Events events

    @Autowired
    private RadioStationsRepository radioStationsRepository

    @Autowired
    private SongsRepository songsRepository

    void 'should process radio station'() {
        given:
            RadioStation station = testRadioStation.create()
        when:
            processRadioStation(station)
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(station.uniqueId)
            foundStation.get().title == station.title
    }

    void 'should process radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
            processRadioStation(radioStation)
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        when:
            processRadioStationStream(stream)
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(radioStation.uniqueId)
            List<RadioStationStreamDocument> streams = foundStation.get().streams
            streams.size() == 1
        and:
            RadioStationStreamDocument resultStream = streams.first()
            resultStream.uniqueId == stream.uniqueId
            resultStream.url == stream.url
    }

    void 'should process song'() {
        given:
            Song song = testSong.create()
        when:
            processSong(song)
        then:
            SongDocument songDocument = songsRepository.findByUniqueId(song.uniqueId).get()
            songDocument.title == song.title
    }

    private void processRadioStation(RadioStation radioStation) {
        Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                RADIO_STATION_CREATED,
                radioStation.uniqueId,
                unpaged()
        )
        assert events.content.size() == 1
        Event event = events.content.first()
        eventsParser.parseEvent(event)
    }

    private void processRadioStationStream(RadioStationStream radioStationStream) {
        Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                RADIO_STATION_STREAM_CREATED,
                radioStationStream.uniqueId,
                unpaged()
        )
        assert events.content.size() == 1
        Event event = events.content.first()
        eventsParser.parseEvent(event)
    }

    private void processSong(Song song) {
        Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                SONG_CREATED,
                song.uniqueId,
                unpaged()
        )
        assert events.content.size() == 1
        Event event = events.content.first()
        eventsParser.parseEvent(event)
    }
}
