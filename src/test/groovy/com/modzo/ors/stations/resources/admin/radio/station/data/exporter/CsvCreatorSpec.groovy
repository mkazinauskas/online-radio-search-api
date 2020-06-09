package com.modzo.ors.stations.resources.admin.radio.station.data.exporter

import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData
import spock.lang.Specification

class CsvCreatorSpec extends Specification {

    CsvCreator testTarget = new CsvCreator()

    void 'should create csv from data'() {
        given:
            List<CsvData> data = [
                    new CsvData(
                            radioStationUniqueId: 'uuid1',
                            radioStationName: 'first name',
                            radioStationEnabled: true,
                            streamUrls: 'http://someurl',
                            streamIsWorking: 'true'
                    ),
                    new CsvData(
                            radioStationUniqueId: 'uuid2',
                            radioStationName: 'first, name',
                            radioStationEnabled: false,
                            streamUrls: 'http://someurl.com?:,',
                            streamIsWorking: 'false'
                    ),
            ]
        when:
            String result = new String(testTarget.write(data))
        then:
            result == 'radioStationEnabled,radioStationName,radioStationUniqueId,streamIsWorking,streamUrls' +
                    '\ntrue,"first name",uuid1,true,http://someurl' +
                    '\nfalse,"first, name",uuid2,false,"http://someurl.com?:,"\n'
    }

}
