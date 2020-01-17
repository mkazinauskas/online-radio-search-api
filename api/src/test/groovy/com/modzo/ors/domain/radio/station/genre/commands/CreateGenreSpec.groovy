package com.modzo.ors.domain.radio.station.genre.commands

import com.modzo.ors.domain.events.Event
import com.modzo.ors.domain.events.Events
import com.modzo.ors.domain.events.GenreCreated
import com.modzo.ors.domain.radio.station.genre.Genre
import com.modzo.ors.domain.radio.station.genre.Genres
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

import static com.modzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.domain.events.Event.Type.GENRE_CREATED
import static org.springframework.data.domain.Pageable.unpaged

class CreateGenreSpec extends IntegrationSpec {
    @Autowired
    private CreateGenre.Handler testTarget

    @Autowired
    private Genres genres

    @Autowired
    private Events events

    void 'should create genre'() {
        given:
            CreateGenre command = new CreateGenre(
                    RandomStringUtils.randomAlphanumeric(10)
            )
        when:
            CreateGenre.Result result = testTarget.handle(command)
        then:
            Genre genre = genres.findById(result.id).get()
            genre.title == command.title
            genre.uniqueId.size() == 20
        and:
            Page<Event> events = events.findAllByType(GENRE_CREATED, unpaged())
        and:
            GenreCreated.Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { GenreCreated.Data data -> data.uniqueId == genre.uniqueId }
            event.title == command.title
            event.uniqueId == genre.uniqueId
    }
}
