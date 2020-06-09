package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.DomainException
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class CreateRadioStationStreamSpec extends IntegrationSpec {

    @Autowired
    private CreateRadioStationStream.Handler testTarget

    void 'should create radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateRadioStationStream command = new CreateRadioStationStream(
                    radioStation.id, '  http://random.url'
            )
        when:
            CreateRadioStationStream.Result result = testTarget.handle(command)
        then:
            RadioStationStream savedStream = radioStationStreams
                    .findByRadioStation_IdAndId(radioStation.id, result.id).get()
            savedStream.url == command.url
    }

    void 'should not allow to create duplicate radio station stream'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateRadioStationStream command = new CreateRadioStationStream(
                    radioStation.id, 'http://random.url/duplicate'
            )
        when:
            testTarget.handle(command)
        then:
            noExceptionThrown()
        when:
            testTarget.handle(command)
        then:
            DomainException exception = thrown(DomainException)
            exception.id == 'FIELD_URL_IS_DUPLICATE_FOR_RADIO_STATION'
            exception.message == 'Field url = `http://random.url/duplicate` is duplicate ' +
                    "for radio station with id = `${radioStation.id}`"
    }
}
