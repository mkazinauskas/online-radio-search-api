package com.modzo.ors.stations.domain.song.commands

import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class DeleteSongSpec extends IntegrationSpec {

    @Autowired
    private DeleteSong.Handler testTarget

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
    }
}
