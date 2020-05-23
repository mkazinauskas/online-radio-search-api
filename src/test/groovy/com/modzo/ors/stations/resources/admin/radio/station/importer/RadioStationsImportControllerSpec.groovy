package com.modzo.ors.stations.resources.admin.radio.station.importer

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamSource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK

class RadioStationsImportControllerSpec extends IntegrationSpec {

    void 'admin should import radio stations from file'() {
        given:
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>()
            body.add('file', readClasspathResource())
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/radio-stations/importer',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(TestUsers.ADMIN))
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                            .body(body)
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
        and:
            radioStations.findByTitle('Chilis - IFC Qatar - Retail Music International').isPresent()
        and:
            radioStationStreams.findByUrl('http://162.252.85.85:7548').isPresent()
    }

    private static InputStreamSource readClasspathResource() {
        return new ClassPathResource('/radio-stations-importer/radio-stations.csv')
    }
}
