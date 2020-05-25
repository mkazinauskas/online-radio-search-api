package com.modzo.ors.stations.resources.admin.radio.station.data.exporter

import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData
import spock.lang.Specification

class CsvCreatorSpec extends Specification {

    CsvCreator testTarget = new CsvCreator()

    void 'should create csv from data'() {
        given:
            List<CsvData> data = [
                    new CsvData(radioStationName: 'first name', streamUrls: 'http://someurl'),
                    new CsvData(radioStationName: 'first, name', streamUrls: 'http://someurl.com?:,'),
            ]
        when:
            String result = new String(testTarget.write(data))
        then:
            result == 'radioStationName,streamUrls\n"first name",http://someurl\n"first, name","http://someurl.com?:,"\n'
    }

}
