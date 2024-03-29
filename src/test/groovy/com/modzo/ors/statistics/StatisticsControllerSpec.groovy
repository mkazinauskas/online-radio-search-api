package com.modzo.ors.statistics

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class StatisticsControllerSpec extends IntegrationSpec {

    void 'admin should retrieve statistics'() {
        when:
            ResponseEntity<StatisticsResponse> result = restTemplate.exchange(
                    '/statistics',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    StatisticsResponse
            )
        then:
            result.statusCode == OK
        and:
            result.body.statistics.get(StatisticProvider.Type.RADIO_STATIONS).size() == 2
            result.body.statistics.get(StatisticProvider.Type.RADIO_STATION_STREAMS).size() == 2
            result.body.statistics.get(StatisticProvider.Type.SEARCHED_QUERIES).size() == 2
    }
}
