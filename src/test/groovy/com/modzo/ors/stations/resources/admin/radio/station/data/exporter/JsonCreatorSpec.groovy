package com.modzo.ors.stations.resources.admin.radio.station.data.exporter

import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class JsonCreatorSpec extends Specification {

    JsonCreator testTarget = new JsonCreator()

    void 'should create csv from data'() {
        given:
            List<BackupData> data = [
                    new BackupData(
                            'uuid1',
                            'first name',
                            true,
                            [
                                    new BackupData.Stream(
                                            'http://someurl.com?:,',
                                            true
                                    )
                            ]
                    ),
                    new BackupData(
                            'uuid2',
                            'first, name',
                            false,
                            [
                                    new BackupData.Stream(
                                            'http://someurl2.com?:,',
                                            false
                                    )
                            ]
                    ),
            ]
        when:
            String result = new String(testTarget.write(data))
        then:
            result == new ClassPathResource('/radio-stations-importer/json-creator/expected.json').getFile().text
    }

}
