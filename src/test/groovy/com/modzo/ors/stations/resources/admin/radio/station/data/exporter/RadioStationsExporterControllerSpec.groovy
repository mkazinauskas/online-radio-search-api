package com.modzo.ors.stations.resources.admin.radio.station.data.exporter

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.resources.IntegrationSpec
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvMapperConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.OK

class RadioStationsExporterControllerSpec extends IntegrationSpec {

    void 'admin should export radio stations from file'() {
        given:
            testRadioStationStream.create()
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/radio-stations/exporter?size=1',
                    HttpMethod.GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(TestUsers.ADMIN))
                            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
        and:
            List<CsvData> data = CsvMapperConfiguration.constructReader().readValues(response.body).readAll()
            data.size() == 1
            with(data.first()) {
                radioStations.findByTitle(radioStationName).isPresent()
                streamUrls?.split('\\|')?.each { url ->
                    assert radioStationStreams.findByUrl(url).isPresent()
                }
            }
    }
}
