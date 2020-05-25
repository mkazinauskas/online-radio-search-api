package com.modzo.ors.stations.resources.admin.radio.station.data.importer

import com.fasterxml.jackson.dataformat.csv.CsvMappingException
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamSource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class CsvReaderSpec extends Specification {

    CsvReader testTarget = new CsvReader()

    void 'should parse file'() {
        given:
            MultipartFile file = file('withIpAddress')
        when:
            List<CsvData> result = testTarget.read(file)
        then:
            result.size() == 1
            with(result.first()) {
                radioStationName == 'Chilis - IFC Qatar - Retail Music International'
                streamUrls == 'http://162.252.85.85:7548'
            }
    }

    void 'should parse file with empty column'() {
        given:
            MultipartFile file = file('withEmptyColumn')
        when:
            List<CsvData> result = testTarget.read(file)
        then:
            result.size() == 1
            with(result.first()) {
                radioStationName == null
                streamUrls == 'http://162.252.85.85:3000'
            }
    }

    void 'should fail on missing column'() {
        given:
            MultipartFile file = file('withMissingColumn')
        when:
            testTarget.read(file)
        then:
            CsvMappingException ex = thrown()
            ex.message.startsWith('Not enough column values: expected 2, found 1')
    }

    void 'should fail when file is with additional column'() {
        given:
            MultipartFile file = file('withAdditionalColumn')
        when:
            testTarget.read(file)
        then:
            CsvMappingException ex = thrown()
            String expected = 'Too many entries: expected at most 2 (value #2 (25 chars) "http://162.252.85.85:3000")'
            ex.message.startsWith expected
    }

    private static MockMultipartFile file(String file) {
        InputStreamSource source = new ClassPathResource("/radio-stations-importer/file-reader/${file}.csv")
        return new MockMultipartFile('radioStations.csv', source.inputStream)
    }

}
