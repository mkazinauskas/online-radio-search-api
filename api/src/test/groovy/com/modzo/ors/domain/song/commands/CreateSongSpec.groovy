package com.modzo.ors.domain.song.commands

import com.modzo.ors.domain.events.Event
import com.modzo.ors.domain.events.Events
import com.modzo.ors.domain.events.SongCreated
import com.modzo.ors.domain.song.Song
import com.modzo.ors.domain.song.Songs
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

import static com.modzo.ors.domain.events.DomainEvent.Data.deserialize
import static com.modzo.ors.domain.events.Event.Type.SONG_CREATED
import static org.springframework.data.domain.Pageable.unpaged

class CreateSongSpec extends IntegrationSpec {
    @Autowired
    private CreateSong.Handler testTarget

    @Autowired
    private Songs songs

    @Autowired
    private Events events

    void 'should create song'() {
        given:
            CreateSong command = new CreateSong(
                    RandomStringUtils.randomAlphanumeric(10),
            )
        when:
            CreateSong.Result result = testTarget.handle(command)
        then:
            Song song = songs.findById(result.id).get()
            song.title == command.title
            song.uniqueId.size() == 40
        and:
            Page<Event> events = events.findAllByType(SONG_CREATED, unpaged())
        and:
            SongCreated.Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { SongCreated.Data data -> data.uniqueId == song.uniqueId }
            event.title == command.title
            event.uniqueId == song.uniqueId
    }
}
