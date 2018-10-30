package com.mozdzo.ors.search.reader

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.commands.UpdateRadioStation
import com.mozdzo.ors.domain.radio.station.genre.Genre
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.UpdateRadioStationStream
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import com.mozdzo.ors.search.*
import com.mozdzo.ors.search.parsedevents.ParsedEvents
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

    @Autowired
    private UpdateRadioStation.Handler updateRadioStationHandler

    @Autowired
    private GenresRepository genresRepository

    @Autowired
    private ParsedEvents parsedEvents

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

    void 'should process genre'() {
        given:
            Genre genre = testGenre.create()
        when:
            processGenre(genre)
        then:
            GenreDocument genreDocument = genresRepository.findByUniqueId(genre.uniqueId).get()
            genreDocument.uniqueId == genre.uniqueId
            genreDocument.title == genre.title
    }

    void 'should process updated radio station'() {
        given:
            RadioStation station = testRadioStation.create()
            processRadioStation(station)
        and:
            String newTitle = testRadioStation.randomTitle()
            String newWebsite = testRadioStation.randomWebsite()
        and:
            Genre genre = testGenre.create()
            processGenre(genre)
        when:
            updateRadioStationHandler.handle(
                    new UpdateRadioStation(station.id,
                            new UpdateRadioStation.Data(
                                    newTitle,
                                    newWebsite,
                                    [genre] as Set
                            )
                    )
            )
            processRadioStationUpdate(station)
        then:
            RadioStationDocument foundStation = radioStationsRepository
                    .findByUniqueId(station.uniqueId).get()

            foundStation.title == newTitle
            foundStation.website == newWebsite
            foundStation.genres.size() == 1
            with(foundStation.genres.first() as GenreDocument) {
                uniqueId == genre.uniqueId
                title == genre.title
            }
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

    private void processRadioStationUpdate(RadioStation station) {
        processEvent(RADIO_STATION_UPDATED, station.uniqueId)
    }

    private void processGenre(Genre genre) {
        processEvent(GENRE_CREATED, genre.uniqueId)
    }

    private void processRadioStation(RadioStation radioStation) {
        processEvent(RADIO_STATION_CREATED, radioStation.uniqueId)
    }

    private void processRadioStationStream(RadioStationStream radioStationStream) {
        processEvent(RADIO_STATION_STREAM_CREATED, radioStationStream.uniqueId)
    }

    private void processRadioStationStreamUpdate(RadioStationStream radioStationStream) {
        processEvent(RADIO_STATION_STREAM_UPDATED, radioStationStream.uniqueId)
    }

    private void processSong(Song song) {
        processEvent(SONG_CREATED, song.uniqueId)
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

        assert parsedEvents.findByEventId(event.id.toString()).get()
    }
}
