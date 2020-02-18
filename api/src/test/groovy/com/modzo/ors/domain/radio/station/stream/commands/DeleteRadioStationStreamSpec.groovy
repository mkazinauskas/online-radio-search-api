package com.modzo.ors.domain.radio.station.stream.commands

import com.modzo.ors.domain.events.Event
import com.modzo.ors.domain.events.Events
import com.modzo.ors.domain.radio.station.RadioStation
import com.modzo.ors.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.domain.radio.station.stream.RadioStationStreams
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.domain.events.Event.Type.RADIO_STATION_STREAM_DELETED
import static com.modzo.ors.domain.events.RadioStationStreamDeleted.Data
import static org.springframework.data.domain.Pageable.unpaged

class DeleteRadioStationStreamSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStationStream.Handler testTarget

    @Autowired
    private RadioStationStreams radioStationStreams

    @Autowired
    private Events events

    void 'should delete radio station stream'() {
        given:
            RadioStation testRadioStation = testRadioStation.create()
            RadioStationStream testRadioStationStream = testRadioStationStream.create(testRadioStation.id)
        expect:
            radioStationStreams.findById(testRadioStationStream.id).isPresent()
        when:
            DeleteRadioStationStream command = new DeleteRadioStationStream(
                    testRadioStationStream.radioStationId,
                    testRadioStationStream.id
            )
            testTarget.handle(command)
        then:
            !radioStationStreams.findById(testRadioStationStream.id).isPresent()
        and:
            Page<Event> events = events.findAllByType(RADIO_STATION_STREAM_DELETED, unpaged())
        and:
            Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { Data data -> data.uniqueId == testRadioStationStream.uniqueId }
            event.radioStationUniqueId == testRadioStation.uniqueId
    }
}
