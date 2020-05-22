package com.modzo.ors.stations.resources.admin.radio.station.importer

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamSource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class FileReaderSpec extends Specification {

    FileReader testTarget = new FileReader()

    void 'should check if file is parsable'() {
        given:
            MultipartFile file = new MockMultipartFile('radioStations.csv', resource('withIpAddress').inputStream)
        when:
            List<ImportEntry> result = testTarget.read(file)
        then:
            result.size() == 1
            with(result.first()) {
                radioStationName == 'Chilis - IFC Qatar - Retail Music International'
                streamUrl == 'http://162.252.85.85:7548'
            }
    }

    private static InputStreamSource resource(String fileName) {
        return new ClassPathResource("/radio-stations-importer/file-reader/${fileName}.csv")
    }
}
