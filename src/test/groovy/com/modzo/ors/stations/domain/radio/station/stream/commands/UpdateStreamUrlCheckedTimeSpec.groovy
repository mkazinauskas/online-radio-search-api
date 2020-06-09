package com.modzo.ors.stations.domain.radio.station.stream.commands

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import java.time.ZonedDateTime

class UpdateStreamUrlCheckedTimeSpec extends IntegrationSpec {

    @Autowired
    private UpdateStreamUrlCheckedTime.Handler testTarget

    void 'should update radio station stream url checked time'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create(StreamUrl.Type.SONGS)
        and:
            ZonedDateTime checkedTime = ZonedDateTime.now()
        and:
            UpdateStreamUrlCheckedTime command = new UpdateStreamUrlCheckedTime(
                    streamUrl.id,
                    checkedTime
            )
        when:
            testTarget.handle(command)
        then:
            StreamUrl savedUrl = streamUrls.findById(streamUrl.id).get()
        and:
            savedUrl.checked.toInstant() == checkedTime.toInstant()
    }
}
