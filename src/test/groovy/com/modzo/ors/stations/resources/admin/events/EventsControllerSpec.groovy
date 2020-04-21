package com.modzo.ors.stations.resources.admin.events

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.events.domain.Event
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.GET

class EventsControllerSpec extends IntegrationSpec {

    void 'unauthorized user should not fetch events'() {
        when:
            ResponseEntity<EventsModel> result = restTemplate.exchange(
                    '/admin/events?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    EventsModel
            )
        then:
            result.statusCode == HttpStatus.UNAUTHORIZED
    }

    void 'admin should fetch events'() {
        given:
            testRadioStation.create()
        and:
            Event event = events.findAll().first()
        when:
            ResponseEntity<EventsModel> result = restTemplate.exchange(
                    '/admin/events?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    EventsModel
            )
        then:
            result.statusCode == HttpStatus.OK
        and:
            with(result.body) {
                EventResponse response = it.content*.content.find { it.entityUniqueId == event.entityUniqueId }

                with(response) {
                    response.id == event.id
                    response.entityUniqueId == event.entityUniqueId
                    response.body == event.body
                    response.type == event.type
                    response.created.toInstant() == event.created.toInstant()
                }
            }
    }

    void 'admin should fetch event'() {
        given:
            testRadioStation.create()
        and:
            Event event = events.findAll().first()
        when:
            ResponseEntity<EventModel> result = restTemplate.exchange(
                    "/admin/events/${event.id}?size=100&page=0",
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    EventModel
            )
        then:
            result.statusCode == HttpStatus.OK
        and:
            with(result.body) {
                EventResponse response = it.content
                        .find { it.entityUniqueId == event.entityUniqueId }

                with(response) {
                    response.id == event.id
                    response.entityUniqueId == event.entityUniqueId
                    response.body == event.body
                    response.type == event.type
                    response.created.toInstant() == event.created.toInstant()
                }
            }
    }
}
