package com.modzo.ors.resources

import com.modzo.ors.helpers.TestGenre
import com.modzo.ors.helpers.TestRadioStation
import com.modzo.ors.helpers.TestRadioStationSong
import com.modzo.ors.helpers.TestRadioStationStream
import com.modzo.ors.helpers.TestSong
import com.modzo.ors.setup.TestWireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
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
    TestSong testSong

    @Autowired
    TestRadioStationSong testRadioStationSong

    @Autowired
    TestWireMockServer testWiremockServer
}