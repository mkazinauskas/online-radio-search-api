package com.mozdzo.ors.search.reader

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.UpdateRadioStationStream
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import com.mozdzo.ors.search.*
import com.mozdzo.ors.search.reader.parser.EventParser
import com.mozdzo.ors.search.reader.parser.EventsParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import spock.lang.Unroll

import static com.mozdzo.ors.domain.events.Event.Type.*
import static com.mozdzo.ors.domain.radio.station.stream.RadioStationStream.Type.ACC
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

    @Autowired
    private List<EventParser> eventParsers

    @Autowired
    private UpdateRadioStationStream.Handler updateRadioStationStreamHandler

    @Unroll
    void 'event type `#eventType` should have parser'() {
        expect:
            eventParsers.find { it.eventClass() == eventType.eventClass }
        where:
            eventType << values()
    }

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

    void 'should update radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
            processRadioStation(radioStation)
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
            processRadioStationStream(stream)
        and:
            String newStreamUrl = testRadioStationStream.randomStreamUrl()
        when:
            updateRadioStationStreamHandler.handle(
                    new UpdateRadioStationStream(radioStation.id, stream.id,
                            new UpdateRadioStationStream.Data(
                                    newStreamUrl, 192, ACC)
                    )
            )
            processRadioStationStreamUpdate(stream)
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(radioStation.uniqueId)
            List<RadioStationStreamDocument> streams = foundStation.get().streams
            streams.size() == 1
        and:
            RadioStationStreamDocument resultStream = streams.first()
            resultStream.uniqueId == stream.uniqueId
            resultStream.url == newStreamUrl
            resultStream.bitRate == 192
            resultStream.type == ACC.name()
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

    void 'should process radio station song'() {
        given:
            RadioStation radioStation = testRadioStation.create()
            processRadioStation(radioStation)
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
            processRadioStationStream(stream)
        and:
            Song song = testSong.create()
            processSong(song)
        when:
            RadioStationSong radioStationSong = testRadioStationSong.create(radioStation.id, song.id)
            processRadioStationSong(radioStationSong)
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(radioStation.uniqueId)
            List<RadioStationSongDocument> songs = foundStation.get().songs
            songs.size() == 1
        and:
            RadioStationSongDocument resultSong = songs.first()
            resultSong.uniqueId == radioStationSong.uniqueId
            resultSong.title == song.title
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

    private void processRadioStationStreamUpdate(RadioStationStream radioStationStream) {
        Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                RADIO_STATION_STREAM_UPDATED,
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

    private void processRadioStationSong(RadioStationSong radioStationSong) {
        processEvent(RADIO_STATION_SONG_CREATED, radioStationSong.uniqueId)
    }

    private void processEvent(Event.Type type, String uniqueId) {
        Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                type,
                uniqueId,
                unpaged()
        )
        assert events.content.size() == 1
        Event event = events.content.first()
        eventsParser.parseEvent(event)
    }
}
