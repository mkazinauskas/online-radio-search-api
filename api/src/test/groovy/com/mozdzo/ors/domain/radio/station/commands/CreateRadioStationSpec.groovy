package com.mozdzo.ors.domain.radio.station.commands

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.events.RadioStationCreated
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.RadioStations
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_CREATED
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
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_CREATED, unpaged())
            events.content
                    .collect { it.deserialize(it.type.eventClass) }
                    .find { RadioStationCreated.Data data -> data.uniqueId == savedRadioStation.uniqueId }
    }
}
