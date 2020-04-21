package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_DELETED
import static com.modzo.ors.events.domain.RadioStationStreamDeleted.Data
import static org.springframework.data.domain.Pageable.unpaged

class DeleteRadioStationStreamSpec extends IntegrationSpec {

    @Autowired
    private DeleteRadioStationStream.Handler testTarget

    void 'should delete radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        expect:
            radioStationStreams.findById(stream.id).isPresent()
        when:
            DeleteRadioStationStream command = new DeleteRadioStationStream(
                    stream.radioStationId,
                    stream.id
            )
            testTarget.handle(command)
        then:
            radioStationStreams.findById(stream.id).isEmpty()
        and:
            eventWasCreated(radioStation, stream)
    }

    private void eventWasCreated(RadioStation radioStation, RadioStationStream stream) {
        Page<Event> events = events.findAllByType(RADIO_STATION_STREAM_DELETED, unpaged())
        Data event = events.content
                .collect { deserialize(it.body, it.type.eventClass) }
                .find { Data data -> data.uniqueId == stream.uniqueId }
        event.id == stream.id
        event.radioStationId == radioStation.id
        event.radioStationUniqueId == radioStation.uniqueId
    }
}
