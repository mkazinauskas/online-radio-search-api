package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import java.time.ZonedDateTime

class UpdateRadioStationStreamCheckedTimeSpec extends IntegrationSpec {

    @Autowired
    private UpdateRadioStationStreamCheckedTime.Handler testTarget

    void 'should update radio station stream checked time'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            ZonedDateTime checkedTime = ZonedDateTime.now()
        and:
            UpdateRadioStationStreamCheckedTime command = new UpdateRadioStationStreamCheckedTime(
                    stream.id,
                    checkedTime
            )
        when:
            testTarget.handle(command)
        then:
            RadioStationStream savedStream = radioStationStreams.findById(stream.id).get()
        and:
            savedStream.id == stream.id
            savedStream.checked.toInstant() == checkedTime.toInstant()
    }
}
