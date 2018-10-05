package com.mozdzo.ors.resources

import com.mozdzo.ors.helpers.TestGenre
import com.mozdzo.ors.helpers.TestRadioStation
import com.mozdzo.ors.helpers.TestRadioStationSong
import com.mozdzo.ors.helpers.TestRadioStationStream
import com.mozdzo.ors.helpers.TestSong
import com.mozdzo.ors.setup.TestWireMockServer
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