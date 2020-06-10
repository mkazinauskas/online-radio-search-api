package com.modzo.ors.search

import com.modzo.ors.search.domain.SongDocument
import com.modzo.ors.search.domain.SongsRepository
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

@Component
class TestSongDocument {

    private final SongsRepository songsRepository

    TestSongDocument(SongsRepository songsRepository) {
        this.songsRepository = songsRepository
    }

    SongDocument create(String title = randomTitle()) {
        SongDocument songDocument = new SongDocument(
                randomNumeric(10) as long,
                UUID.randomUUID(),
                title
        )
        songsRepository.save(songDocument)
    }

    private static String randomTitle() {
        randomAlphanumeric(100)
    }
}
