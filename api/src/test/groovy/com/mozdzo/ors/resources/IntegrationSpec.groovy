package com.mozdzo.ors.resources

import com.mozdzo.ors.TokenProvider
import com.mozdzo.ors.helpers.TestRadioStation
import com.mozdzo.ors.helpers.TestRadioStationStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class IntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    TokenProvider tokenProvider

    @Autowired
    TestRadioStation testRadioStation

    @Autowired
    TestRadioStationStream testRadioStationStream
}
