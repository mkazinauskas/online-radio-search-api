package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.RadioStationStreamCheckedTimeUpdated
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import java.time.ZonedDateTime

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static org.springframework.data.domain.Pageable.unpaged

class UpdateRadioStationStreamCheckedTimeSpec extends IntegrationSpec {

    @Autowired
    private UpdateRadioStationStreamCheckedTime.Handler testTarget

    void 'should update radio station stream checked time'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            ZonedDateTime checkedTime = ZonedDateTime.now()
        and:
            UpdateRadioStationStreamCheckedTime command = new UpdateRadioStationStreamCheckedTime(
                    stream.id,
                    checkedTime
            )
        when:
            testTarget.handle(command)
        then:
            RadioStationStream savedStream = radioStationStreams.findById(stream.id).get()
        and:
            Page<Event> events = events.findAllByType(Event.Type.RADIO_STATION_STREAM_CHECKED_TIME_UPDATED, unpaged())
        and:
            RadioStationStreamCheckedTimeUpdated.Data foundEvent = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { RadioStationStreamCheckedTimeUpdated.Data data -> data.uniqueId == savedStream.uniqueId }

            foundEvent.streamId == command.streamId
            foundEvent.uniqueId == stream.uniqueId
            foundEvent.checkedTime == checkedTime
    }
}
