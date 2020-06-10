package com.modzo.ors.stations.domain.radio.station.genre.commands

import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class CreateGenreSpec extends IntegrationSpec {

    @Autowired
    private CreateGenre.Handler testTarget

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
            genre.uniqueId
    }
}
