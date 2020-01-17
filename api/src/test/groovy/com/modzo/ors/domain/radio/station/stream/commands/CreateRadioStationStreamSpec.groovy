package com.modzo.ors.domain.radio.station.stream.commands

import com.modzo.ors.domain.events.Event
import com.modzo.ors.domain.events.Events
import com.modzo.ors.domain.events.RadioStationStreamCreated
import com.modzo.ors.domain.radio.station.RadioStation
import com.modzo.ors.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.domain.radio.station.stream.RadioStationStreams
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.domain.events.DomainEvent.Data.deserialize
import static org.springframework.data.domain.Pageable.unpaged

class CreateRadioStationStreamSpec extends IntegrationSpec {
    @Autowired
    private CreateRadioStationStream.Handler testTarget

    @Autowired
    private RadioStationStreams streams

    @Autowired
    private Events events

    void 'should create radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateRadioStationStream command = new CreateRadioStationStream(
                    radioStation.id, 'http://random.url'
            )
        when:
            CreateRadioStationStream.Result result = testTarget.handle(command)
        then:
            RadioStationStream savedStream = streams.findByRadioStationIdAndId(radioStation.id, result.id).get()
        and:
            Page<Event> events = events.findAllByType(Event.Type.RADIO_STATION_STREAM_CREATED, unpaged())
        and:
            RadioStationStreamCreated.Data foundEvent = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { RadioStationStreamCreated.Data data -> data.uniqueId == savedStream.uniqueId }
            foundEvent.url == command.url
    }
}
