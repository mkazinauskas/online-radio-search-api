package com.modzo.ors.stations.domain.radio.station.commands

import com.modzo.ors.stations.domain.events.Event
import com.modzo.ors.stations.domain.events.Events
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.RadioStations
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.stations.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.stations.domain.events.Event.Type.RADIO_STATION_DELETED
import static com.modzo.ors.stations.domain.events.RadioStationDeleted.Data
import static org.springframework.data.domain.Pageable.unpaged

class DeleteRadioStationSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStation.Handler testTarget

    @Autowired
    private RadioStations radioStations

    @Autowired
    private Events events

    void 'should delete radio station'() {
        given:
            RadioStation testRadioStation = testRadioStation.create()
        expect:
            radioStations.findById(testRadioStation.id).isPresent()
        when:
            DeleteRadioStation command = new DeleteRadioStation(
                    testRadioStation.id
            )
            testTarget.handle(command)
        then:
            !radioStations.findById(testRadioStation.id).isPresent()
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_DELETED, unpaged())
        and:
            Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { Data data -> data.uniqueId == testRadioStation.uniqueId }
            event
    }
}
