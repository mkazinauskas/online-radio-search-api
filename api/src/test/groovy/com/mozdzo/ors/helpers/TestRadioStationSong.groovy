package com.mozdzo.ors.helpers

import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.domain.radio.station.song.commands.CreateRadioStationSong
import com.mozdzo.ors.domain.radio.station.song.commands.GetRadioStationSongByid
import com.mozdzo.ors.domain.song.commands.CreateSong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static java.time.ZonedDateTime.now
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStationSong {

    @Autowired
    private CreateSong.Handler createSongHandler

    @Autowired
    private CreateRadioStationSong.Handler createRadioStationSongHandler

    @Autowired
    private GetRadioStationSongByid.Handler getRadioStationSongHandler

    RadioStationSong create(long radioStationId, long songId) {
        CreateRadioStationSong.Result result = createRadioStationSongHandler.handle(
                new CreateRadioStationSong(songId, radioStationId, now())
        )
        return getRadioStationSongHandler.handle(new GetRadioStationSongByid(radioStationId, result.id))
    }

    RadioStationSong create(long radioStationId) {
        CreateSong createSong = new CreateSong(randomAlphanumeric(100))
        long newSongId = createSongHandler.handle(createSong).id
        return create(radioStationId, newSongId)
    }
}
