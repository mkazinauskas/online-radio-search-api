package com.modzo.ors.stations.domain.radio.station.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.Events
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.RadioStations
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_UPDATED
import static com.modzo.ors.events.domain.RadioStationUpdated.Data
import static org.springframework.data.domain.Pageable.unpaged

class UpdateRadioStationSpec extends IntegrationSpec {

    @Autowired
    private UpdateRadioStation.Handler testTarget

    @Autowired
    private RadioStations radioStations

    @Autowired
    private Events events

    void 'should update radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create(testGenre.create())
        and:
            Genre genre = testGenre.create()
        and:
            UpdateRadioStation command = new UpdateRadioStation(
                    radioStation.id,
                    new UpdateRadioStation.DataBuilder()
                            .setTitle('new title')
                            .setWebsite('awesome website')
                            .setEnabled(true)
                            .setGenres([new UpdateRadioStation.Data.Genre(genre.id)] as Set)
                            .build()
            )
        when:
            testTarget.handle(command)
        then:
            RadioStation savedRadioStation = radioStations.findById(radioStation.id).get()
            savedRadioStation.title == command.data.title
            savedRadioStation.website == command.data.website
            savedRadioStation.enabled == command.data.enabled
            savedRadioStation.genres*.uniqueId.first() == genre.uniqueId
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_UPDATED, unpaged())
        and:
            Data foundEvent = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .findAll { Data data -> data.uniqueId == savedRadioStation.uniqueId }
                    .last()
            foundEvent.title == command.data.title
            foundEvent.website == command.data.website
            foundEvent.enabled == command.data.enabled
            foundEvent.genres.first().title == genre.title
    }
}