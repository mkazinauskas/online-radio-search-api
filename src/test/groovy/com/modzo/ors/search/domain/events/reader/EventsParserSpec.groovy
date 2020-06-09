package com.modzo.ors.search.domain.events.reader

import com.modzo.ors.events.domain.Event
import com.modzo.ors.search.domain.GenreDocument
import com.modzo.ors.search.domain.GenresRepository
import com.modzo.ors.search.domain.RadioStationDocument
import com.modzo.ors.search.domain.RadioStationsRepository
import com.modzo.ors.search.domain.SongDocument
import com.modzo.ors.search.domain.SongsRepository
import com.modzo.ors.search.domain.events.reader.parser.EventParser
import com.modzo.ors.search.domain.events.reader.parser.EventsProcessor
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.domain.radio.station.genre.commands.DeleteGenre
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.domain.song.commands.DeleteSong
import com.modzo.ors.stations.resources.IntegrationSpec
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

@Slf4j
class EventsParserSpec extends IntegrationSpec {

    @Autowired
    private RadioStationsRepository radioStationsRepository

    @Autowired
    private SongsRepository songsRepository

    @Autowired
    private List<EventParser> eventParsers

    @Autowired
    private UpdateRadioStation.Handler updateRadioStationHandler

    @Autowired
    private DeleteSong.Handler deleteSongHandler

    @Autowired
    private DeleteGenre.Handler deleteGenreHandler

    @Autowired
    private GenresRepository genresRepository

    @Autowired
    private EventsProcessor eventsProcessor

    @Unroll
    void 'event type `#eventType` should have parser'() {
        expect:
            eventParsers.find { it.eventClass() == eventType.eventClass }
        where:
            eventType << Event.Type.values()
    }

    void 'should process radio station'() {
        given:
            RadioStation station = testRadioStation.create()
        when:
            eventsProcessor.process()
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(station.uniqueId)
            foundStation.get().title == station.title
    }

    void 'should process genre'() {
        given:
            Genre genre = testGenre.create()
        when:
            eventsProcessor.process()
        then:
            GenreDocument genreDocument = genresRepository.findByUniqueId(genre.uniqueId).get()
            genreDocument.uniqueId == genre.uniqueId
            genreDocument.title == genre.title
    }

    void 'should process deleted genre'() {
        given:
            Genre genre = testGenre.create()
        and:
            deleteGenreHandler.handle(new DeleteGenre(genre.id))
        when:
            eventsProcessor.process()
        then:
            genresRepository.findByUniqueId(genre.uniqueId).isEmpty()
    }

    void 'should process updated radio station'() {
        given:
            RadioStation station = testRadioStation.create()
        and:
            String newTitle = testRadioStation.randomTitle()
            String newWebsite = testRadioStation.randomWebsite()
        and:
            Genre genre = testGenre.create()
        when:
            updateRadioStationHandler.handle(
                    new UpdateRadioStation(station.id,
                            new UpdateRadioStation.DataBuilder()
                                    .setTitle(newTitle)
                                    .setWebsite(newWebsite)
                                    .setEnabled(true)
                                    .setGenres([new UpdateRadioStation.Data.Genre(genre.id)] as Set)
                                    .build()
                    )
            )
            eventsProcessor.process()
        then:
            RadioStationDocument foundStation = radioStationsRepository
                    .findByUniqueId(station.uniqueId).get()

            foundStation.title == newTitle
            foundStation.website == newWebsite
            foundStation.enabled
    }

    void 'should process song'() {
        given:
            Song song = testSong.create()
        when:
            eventsProcessor.process()
        then:
            SongDocument songDocument = songsRepository.findByUniqueId(song.uniqueId).get()
            songDocument.title == song.title
    }

    void 'should process deleted song'() {
        given:
            Song song = testSong.create()
        and:
            deleteSongHandler.handle(new DeleteSong(song.id))
        when:
            eventsProcessor.process()
        then:
            songsRepository.findByUniqueId(song.uniqueId).isEmpty()
    }

}
