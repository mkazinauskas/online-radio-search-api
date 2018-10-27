package com.mozdzo.ors.search.reader

import com.mozdzo.ors.domain.events.Event
import com.mozdzo.ors.domain.events.Events
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.resources.IntegrationSpec
import com.mozdzo.ors.search.RadioStationDocument
import com.mozdzo.ors.search.RadioStationsRepository
import com.mozdzo.ors.search.reader.parser.EventsParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_CREATED
import static org.springframework.data.domain.Pageable.unpaged

class EventsParserSpec extends IntegrationSpec {

    @Autowired
    private EventsParser eventsParser

    @Autowired
    private Events events

    @Autowired
    private RadioStationsRepository repository

    void 'should process event'() {
        given:
            RadioStation station = testRadioStation.create()
        when:
            Page<Event> events = events.findAllByTypeAndEntityUniqueId(
                    RADIO_STATION_CREATED,
                    station.uniqueId,
                    unpaged()
            )
        then:
            events.content.size() == 1
        and:
            Event event = events.content.first()
        when:
            eventsParser.parseEvent(event)
        then:
            Optional<RadioStationDocument> foundStation = repository.findByUniqueId(station.uniqueId)
            foundStation.get().title == station.title
    }
}
