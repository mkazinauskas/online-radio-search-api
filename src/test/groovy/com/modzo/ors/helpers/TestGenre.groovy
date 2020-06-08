package com.modzo.ors.helpers

import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.domain.radio.station.genre.commands.CreateGenre
import com.modzo.ors.stations.domain.radio.station.genre.commands.GetGenre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestGenre {

    @Autowired
    private CreateGenre.Handler createGenreHandler

    @Autowired
    private GetGenre.Handler getGenreHandler

    Genre create(String title = randomAlphanumeric(100)) {
        CreateGenre createGenre = new CreateGenre(title)
        long newGenreId = createGenreHandler.handle(createGenre).id
        return getGenreHandler.handle(new GetGenre(newGenreId))
    }
}
