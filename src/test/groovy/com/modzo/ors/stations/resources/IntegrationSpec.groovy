package com.modzo.ors.stations.resources

import com.modzo.ors.TestUsers
import com.modzo.ors.TokenProvider
import com.modzo.ors.helpers.TestGenre
import com.modzo.ors.helpers.TestRadioStation
import com.modzo.ors.helpers.TestRadioStationSong
import com.modzo.ors.helpers.TestRadioStationStream
import com.modzo.ors.helpers.TestRadioStationStreamUrl
import com.modzo.ors.helpers.TestSong
import com.modzo.ors.search.TestRadioStationDocument
import com.modzo.ors.search.TestSongDocument
import com.modzo.ors.stations.domain.radio.station.RadioStations
import com.modzo.ors.stations.domain.radio.station.genre.Genres
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls
import com.modzo.ors.stations.domain.song.Songs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles('test')
class IntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    TestRadioStation testRadioStation

    @Autowired
    TestGenre testGenre

    @Autowired
    TestRadioStationStream testRadioStationStream

    @Autowired
    TestRadioStationStreamUrl testStreamUrl

    @Autowired
    TestSong testSong

    @Autowired
    TestRadioStationSong testRadioStationSong

    @Autowired
    TestSongDocument testSongDocument

    @Autowired
    TestRadioStationDocument testRadioStationDocument

    @Autowired
    WireMockTestHelper wireMockTestHelper

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    RadioStationStreams radioStationStreams

    @Autowired
    RadioStations radioStations

    @Autowired
    RadioStationSongs radioStationSongs

    @Autowired
    StreamUrls streamUrls

    @Autowired
    Songs songs

    @Autowired
    Genres genres

    String token(TestUsers.TestUser user) {
        tokenProvider.token(user.username, user.password)
    }
}