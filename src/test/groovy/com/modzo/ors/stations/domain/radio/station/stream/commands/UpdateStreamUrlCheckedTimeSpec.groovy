package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.StreamUrlCheckedTimeUpdated
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import java.time.ZonedDateTime

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static org.springframework.data.domain.Pageable.unpaged

class UpdateStreamUrlCheckedTimeSpec extends IntegrationSpec {

    @Autowired
    private UpdateStreamUrlCheckedTime.Handler testTarget

    void 'should update radio station stream url checked time'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create(StreamUrl.Type.SONGS)
        and:
            ZonedDateTime checkedTime = ZonedDateTime.now()
        and:
            UpdateStreamUrlCheckedTime command = new UpdateStreamUrlCheckedTime(
                    streamUrl.id,
                    checkedTime
            )
        when:
            testTarget.handle(command)
        then:
            StreamUrl savedUrl = streamUrls.findById(streamUrl.id).get()
        and:
            Page<Event> events = events.findAllByType(Event.Type.STREAM_URL_CHECKED_TIME_UPDATED, unpaged())
        and:
            StreamUrlCheckedTimeUpdated.Data foundEvent = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { StreamUrlCheckedTimeUpdated.Data data -> data.uniqueId == savedUrl.uniqueId }

            foundEvent.id == command.urlId
            foundEvent.checkedTime.toInstant() == checkedTime.toInstant()
    }
}
