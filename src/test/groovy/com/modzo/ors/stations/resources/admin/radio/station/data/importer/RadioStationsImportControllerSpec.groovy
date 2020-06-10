package com.modzo.ors.stations.resources.admin.radio.station.data.importer

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.configuration.exception.handler.DomainApiError
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamSource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

class RadioStationsImportControllerSpec extends IntegrationSpec {

    void 'admin should import radio stations from file'() {
        given:
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>()
            body.add('file', readClasspathResource('radio-stations'))
            body.add('importUniqueIds', true)
        when:
            ResponseEntity<String> response = doImport(body)
        then:
            response.statusCode == OK
        and:
            RadioStation foundStation = radioStations
                    .findByTitle('Chilis - IFC Qatar - Retail Music International').get()
            foundStation.enabled
            foundStation.uniqueId == 'STfTz3JPxxO3EDnkUpBf'
        and:
            RadioStationStream stream1 = radioStationStreams.findByUrl('http://162.252.85.85:7548').get()
            !stream1.working
        and:
            RadioStationStream stream2 = radioStationStreams.findByUrl('http://162.252.85.85:7547').get()
            !stream2.working
    }

    void 'admin should import radio stations from file with no UUID'() {
        given:
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>()
            body.add('file', readClasspathResource('radio-stations-no-uuid-import'))
            body.add('importUniqueIds', false)
        when:
            ResponseEntity<String> response = doImport(body)
        then:
            response.statusCode == OK
        and:
            RadioStation foundStation = radioStations
                    .findByTitle('Chilis - IFC Qatar - Retail Music International NO UUID').get()
            foundStation.enabled
            foundStation.uniqueId != 'DONOTIMPORT'
        and:
            RadioStationStream stream1 = radioStationStreams.findByUrl('http://162.252.85.85:9999').get()
            !stream1.working
        and:
            RadioStationStream stream2 = radioStationStreams.findByUrl('http://162.252.85.85:9998').get()
            !stream2.working
    }

    void 'admin should import radio stations from file with title longer that 100 symbols'() {
        given:
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>()
            body.add('file', readClasspathResource('radio-stations-too-long-title'))
            body.add('importUniqueIds', true)
        when:
            ResponseEntity<DomainApiError> response = doImportWithError(body)
        then:
            response.statusCode == BAD_REQUEST
            response.body.id == 'FAILED_TO_IMPORT_RADIO_STATIONS'
            response.body.fields.first() == 'file'
            response.body.message == 'Field title cannot be longer than 100 characters'
    }

    void 'admin should skip import of radio stations from file with url longer that 100 symbols'() {
        given:
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>()
            body.add('file', readClasspathResource('radio-stations-too-long-url'))
            body.add('importUniqueIds', true)
        when:
            ResponseEntity<DomainApiError> response = doImportWithError(body)
        then:
            response.statusCode == BAD_REQUEST
            response.body.id == 'FAILED_TO_IMPORT_RADIO_STATIONS'
            response.body.fields.first() == 'file'
            response.body.message == 'Field url cannot be longer than 100 characters'
    }

    private ResponseEntity<String> doImport(LinkedMultiValueMap<String, Object> body) {
        restTemplate.exchange(
                '/admin/radio-stations/importer',
                POST,
                HttpEntityBuilder.builder()
                        .bearer(token(TestUsers.ADMIN))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                        .body(body)
                        .build(),
                String
        )
    }

    private ResponseEntity<DomainApiError> doImportWithError(LinkedMultiValueMap<String, Object> body) {
        restTemplate.exchange(
                '/admin/radio-stations/importer',
                POST,
                HttpEntityBuilder.builder()
                        .bearer(token(TestUsers.ADMIN))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                        .body(body)
                        .build(),
                DomainApiError
        )
    }

    private static InputStreamSource readClasspathResource(String name) {
        return new ClassPathResource("/radio-stations-importer/${name}.json")
    }
}
