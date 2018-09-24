package com.mozdzo.ors.helpers

import com.mozdzo.ors.domain.radio.station.song.Song
import com.mozdzo.ors.domain.radio.station.song.commands.CreateSong
import com.mozdzo.ors.domain.radio.station.song.commands.GetSong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static java.time.ZonedDateTime.now
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestSong {

    @Autowired
    private CreateSong.Handler createSongHandler

    @Autowired
    private GetSong.Handler getSongHandler

    Song create(long radioStationId) {
        CreateSong createSong = new CreateSong(radioStationId, randomAlphanumeric(100), now())
        long newSongId = createSongHandler.handle(createSong).id

        return getSongHandler.handle(new GetSong(radioStationId, newSongId))
    }
}
