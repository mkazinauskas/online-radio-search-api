package com.modzo.ors.domain.radio.station.stream.commands

import com.modzo.ors.domain.events.Event
import com.modzo.ors.domain.events.Events
import com.modzo.ors.domain.events.RadioStationStreamUpdated
import com.modzo.ors.domain.radio.station.RadioStation
import com.modzo.ors.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.domain.radio.station.stream.RadioStationStreams
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.domain.radio.station.stream.RadioStationStream.Type.ACC
import static org.springframework.data.domain.Pageable.unpaged

class UpdateRadioStationStreamSpec extends IntegrationSpec {
    @Autowired
    private UpdateRadioStationStream.Handler testTarget

    @Autowired
    private RadioStationStreams streams

    @Autowired
    private Events events

    void 'should create radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            UpdateRadioStationStream command = new UpdateRadioStationStream(
                    radioStation.id, stream.id,
                    new UpdateRadioStationStream.Data('new test url', 993, ACC)
            )
        when:
            testTarget.handle(command)
        then:
            RadioStationStream savedStream = streams.findByRadioStationIdAndId(radioStation.id, stream.id).get()
        and:
            Page<Event> events = events.findAllByType(Event.Type.RADIO_STATION_STREAM_UPDATED, unpaged())
        and:
            RadioStationStreamUpdated.Data foundEvent = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { RadioStationStreamUpdated.Data data -> data.uniqueId == savedStream.uniqueId }

            foundEvent.url == command.data.url
            foundEvent.type == command.data.type.name()
            foundEvent.bitRate == command.data.bitRate
    }
}
