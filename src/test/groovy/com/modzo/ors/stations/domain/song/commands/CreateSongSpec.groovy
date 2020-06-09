package com.modzo.ors.stations.domain.song.commands

import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class CreateSongSpec extends IntegrationSpec {

    @Autowired
    private CreateSong.Handler testTarget

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
    }
}
