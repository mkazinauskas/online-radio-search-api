package com.modzo.ors.stations.resources.admin.radio.station.data

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CsvMapperConfigurationSpec extends Specification {

    @Shared
    ObjectWriter WRITER = CsvMapperConfiguration.constructWriter()

    @Shared
    ObjectReader READER = CsvMapperConfiguration.constructReader()

    @Unroll
    void 'should read created csv file'() {
        given:
            CsvData data = new CsvData(radioStationName: radioStationName, streamUrls: streamUrl)
            ByteArrayOutputStream output = new ByteArrayOutputStream()
        when:
            WRITER.writeValue(output, [data])
        then:
            output.toByteArray().length > 0
        when:
            List<CsvData> result = READER.readValues(output.toByteArray()).readAll()
        then:
            result.size() == 1
            with(result.first()) { entry ->
                entry.radioStationName == radioStationName
                entry.streamUrls == streamUrl
            }
        where:
            radioStationName            | streamUrl
            'Simple radio station name' | 'https://test.com/mp3'
            '!@#$%^&*()_,.,"'           | '!@#$%^&*()_,.;"'
    }
}
