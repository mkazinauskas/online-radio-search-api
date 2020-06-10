package com.modzo.ors.stations.resources.admin.radio.station.data.exporter

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupMapperConfiguration
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
            List<BackupData> data = BackupMapperConfiguration.constructReader().readValues(response.body).readAll()
            data.size() == 1
            with(data.first()) {
                Optional<RadioStation> radioStation = radioStations.findByTitle(title)
                radioStation.isPresent()
                radioStation.get().uniqueId == UUID.fromString(uniqueId)
                if (streams) {
                    streams.each { stream ->
                        RadioStationStream savedStream = radioStationStreams.findByUrl(stream.url).get()
                        savedStream.working == stream.working
                        true
                    }
                }
            }
    }

}
