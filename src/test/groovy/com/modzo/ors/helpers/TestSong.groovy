package com.modzo.ors.helpers

import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.domain.song.commands.CreateSong
import com.modzo.ors.stations.domain.song.commands.GetSong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestSong {

    @Autowired
    private CreateSong.Handler createSongHandler

    @Autowired
    private GetSong.Handler getSongHandler

    Song create() {
        CreateSong createSong = new CreateSong(randomAlphanumeric(100))
        long newSongId = createSongHandler.handle(createSong).id

        return getSongHandler.handle(new GetSong(newSongId))
    }
}
