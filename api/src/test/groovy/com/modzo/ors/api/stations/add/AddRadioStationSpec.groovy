package com.modzo.ors.api.stations.add

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AddRadioStationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def 'should add radio station'() {
        given:
            AddRadioStationRequest request = new AddRadioStationRequest(url: 'http://someradiostation.com')
        when:
            ResponseEntity response = restTemplate.postForEntity('/stations', request, String)
        then:
            response.statusCode == HttpStatus.CREATED
            response.headers.getLocation().toString() == '/stations'
    }
}
