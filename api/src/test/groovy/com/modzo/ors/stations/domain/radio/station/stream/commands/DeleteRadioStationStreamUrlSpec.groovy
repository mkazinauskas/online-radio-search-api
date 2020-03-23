package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.RadioStationStreamUrlDeleted
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_URL_DELETED
import static org.springframework.data.domain.Pageable.unpaged

class DeleteRadioStationStreamUrlSpec extends IntegrationSpec {

    @Autowired
    DeleteRadioStationStreamUrl.Handler deleteRadioStationStreamUrl

    void 'should delete radio station stream url'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create()
        when:
            deleteRadioStationStreamUrl.handle(
                    new DeleteRadioStationStreamUrl(
                            streamUrl.stream.radioStationId, streamUrl.stream.id, streamUrl.id
                    )
            )
        then:
            streamUrls.findById(streamUrl.id).isEmpty()
        and:
            eventWasCreated(streamUrl)
    }

    private void eventWasCreated(StreamUrl streamUrl) {
        Page<Event> events = events.findAllByType(RADIO_STATION_STREAM_URL_DELETED, unpaged())
        RadioStationStreamUrlDeleted.Data event = events.content
                .collect { deserialize(it.body, it.type.eventClass) }
                .find { RadioStationStreamUrlDeleted.Data data -> data.uniqueId == streamUrl.uniqueId }
        event.id == streamUrl.id
        event.uniqueId == streamUrl.uniqueId
        event.streamId == streamUrl.stream.id
        event.streamUniqueId == streamUrl.stream.uniqueId
    }
}
