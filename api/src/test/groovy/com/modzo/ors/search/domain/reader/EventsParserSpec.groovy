package com.modzo.ors.search.domain.reader

import com.modzo.ors.stations.domain.events.Event
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStream
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import com.modzo.ors.search.domain.*
import com.modzo.ors.search.domain.reader.parser.EventParser
import com.modzo.ors.search.domain.reader.parser.EventsProcessor
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream.Type.ACC

class EventsParserSpec extends IntegrationSpec {

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
                            new UpdateRadioStation.Data(
                                    newTitle,
                                    newWebsite,
                                    [genre] as Set
                            )
                    )
            )
            eventsProcessor.process()
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
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        when:
            eventsProcessor.process()
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
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            String newStreamUrl = testRadioStationStream.randomStreamUrl()
        when:
            updateRadioStationStreamHandler.handle(
                    new UpdateRadioStationStream(radioStation.id, stream.id,
                            new UpdateRadioStationStream.Data(
                                    newStreamUrl, 192, ACC)
                    )
            )
            eventsProcessor.process()
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
            eventsProcessor.process()
        then:
            SongDocument songDocument = songsRepository.findByUniqueId(song.uniqueId).get()
            songDocument.title == song.title
    }

    void 'should process radio station song'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create()
        when:
            RadioStationSong radioStationSong = testRadioStationSong.create(radioStation.id, song.id)
            eventsProcessor.process()
        then:
            Optional<RadioStationDocument> foundStation = radioStationsRepository.findByUniqueId(radioStation.uniqueId)
            List<RadioStationSongDocument> songs = foundStation.get().songs
            songs.size() == 1
        and:
            RadioStationSongDocument resultSong = songs.first()
            resultSong.uniqueId == radioStationSong.uniqueId
            resultSong.title == song.title
    }
}
