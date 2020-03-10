package com.modzo.ors.stations.domain.song.commands

import com.modzo.ors.events.domain.Event
import com.modzo.ors.events.domain.Events
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.domain.song.Songs
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page

import static com.modzo.ors.events.domain.DomainEvent.Data.deserialize
import static com.modzo.ors.events.domain.Event.Type.SONG_DELETED
import static com.modzo.ors.events.domain.SongDeleted.Data
import static org.springframework.data.domain.Pageable.unpaged

class DeleteSongSpec extends IntegrationSpec {

    @Autowired
    private DeleteSong.Handler testTarget

    @Autowired
    private Songs songs

    @Autowired
    private Events events

    void 'should delete song'() {
        given:
            Song song = testSong.create()
        expect:
            songs.findById(song.id).isPresent()
        when:
            DeleteSong command = new DeleteSong(song.id)
            testTarget.handle(command)
        then:
            !songs.findById(song.id).isPresent()
        and:
            Page<Event> events = events.findAllByType(SONG_DELETED, unpaged())
        and:
            Data event = events.content
                    .collect { deserialize(it.body, it.type.eventClass) }
                    .find { Data data -> data.uniqueId == song.uniqueId }
            event
    }
}
