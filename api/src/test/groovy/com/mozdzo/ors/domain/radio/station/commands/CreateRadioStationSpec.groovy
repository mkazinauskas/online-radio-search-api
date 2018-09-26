package com.mozdzo.ors.domain.radio.station.commands

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.RadioStations
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

import static com.mozdzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_CREATED
import static com.mozdzo.ors.domain.events.RadioStationCreated.Data
import static org.springframework.data.domain.Pageable.unpaged

class CreateRadioStationSpec extends IntegrationSpec {

    @Autowired
    private CreateRadioStation.Handler testTarget

    @Autowired
    private RadioStations radioStations

    @Autowired
    private Events events

    void 'should create radio station'() {
        given:
            CreateRadioStation command = new CreateRadioStation(
                    RandomStringUtils.randomAlphanumeric(10)
            )
        when:
            CreateRadioStation.Result result = testTarget.handle(command)
        then:
            RadioStation savedRadioStation = radioStations.findById(result.id).get()
            savedRadioStation.title == command.title
            savedRadioStation.id == result.id
            savedRadioStation.uniqueId.size() == 20
            savedRadioStation.genres.empty
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_CREATED, unpaged())
        and:
            Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { Data data -> data.uniqueId == savedRadioStation.uniqueId }
            event.title == command.title
    }
}
