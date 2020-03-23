package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.RadioStationStreamUrlCreated
import com.modzo.ors.helpers.FakeUrl
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_URL_CREATED
import static org.springframework.data.domain.Pageable.unpaged

class CreateRadioStationStreamUrlSpec extends IntegrationSpec {

    @Autowired
    CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl

    void 'should add radio station stream url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            CreateRadioStationStreamUrl request = new CreateRadioStationStreamUrl(
                    stream.radioStationId,
                    stream.id,
                    StreamUrl.Type.INFO,
                    FakeUrl.create()
            )
        when:
            createRadioStationStreamUrl.handle(request)
        then:
            RadioStationStream savedStream = radioStationStreams
                    .findByRadioStation_IdAndId(stream.radioStationId, stream.id)
                    .get()

            savedStream.urls.size() == 1
            StreamUrl savedUrl = savedStream.urls.get(StreamUrl.Type.INFO)
            savedUrl.url == request.url
        and:
            eventWasCreated(savedUrl)
    }

    private void eventWasCreated(StreamUrl streamUrl) {
        Page<Event> events = events.findAllByType(RADIO_STATION_STREAM_URL_CREATED, unpaged())
        RadioStationStreamUrlCreated.Data event = events.content
                .collect { deserialize(it.body, it.type.eventClass) }
                .find { RadioStationStreamUrlCreated.Data data -> data.uniqueId == streamUrl.uniqueId }
        event.id == streamUrl.id
        event.uniqueId == streamUrl.uniqueId
        event.created.toInstant() == streamUrl.created.toInstant()
        event.streamId == streamUrl.stream.id
        event.streamUniqueId == streamUrl.stream.uniqueId
        event.url == streamUrl.url
        event.type == streamUrl.type
    }
}
