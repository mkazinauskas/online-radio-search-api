package com.modzo.ors

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
class OnlineRadioSearchApplicationSpec extends Specification {

    def 'should load context'() {
        expect:
        1 == 1
    }
}
