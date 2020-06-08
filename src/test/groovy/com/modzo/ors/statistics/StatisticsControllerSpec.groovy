package com.modzo.ors.statistics

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class StatisticsControllerSpec extends IntegrationSpec {

    void 'admin should retrieve statistics'() {
        when:
            ResponseEntity<StatisticsResponse> result = restTemplate.exchange(
                    '/admin/statistics',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    StatisticsResponse
            )
        then:
            result.statusCode == OK
        and:
            result.body
    }
}
