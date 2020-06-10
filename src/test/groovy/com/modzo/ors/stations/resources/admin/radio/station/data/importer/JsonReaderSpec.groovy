package com.modzo.ors.stations.resources.admin.radio.station.data.importer

import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamSource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class JsonReaderSpec extends Specification {

    JsonReader testTarget = new JsonReader()

    void 'should parse file'() {
        given:
            MultipartFile file = file('expected')
        when:
            List<BackupData> result = testTarget.read(file)
        then:
            result.size() == 2
        and:
            with(result.get(0)) {
                uniqueId == 'uuid1'
                title == 'first name'
                enabled
                streams.size() == 2
                with(streams.get(0)) {
                    url == 'http://someurl.com?:,'
                    working
                }
                with(streams.get(1)) {
                    url == 'http://someurl.com},'
                    !working
                }
            }
        and:
            with(result.get(1)) {
                uniqueId == 'uuid2'
                title == 'first, name'
                !enabled
                streams.size() == 1
                with(streams.get(0)) {
                    url == 'http://someurl2.com?:,'
                    !working
                }
            }
    }

    private static MockMultipartFile file(String file) {
        InputStreamSource source = new ClassPathResource("/radio-stations-importer/json-reader/${file}.json")
        return new MockMultipartFile('radioStations.csv', source.inputStream)
    }

}
