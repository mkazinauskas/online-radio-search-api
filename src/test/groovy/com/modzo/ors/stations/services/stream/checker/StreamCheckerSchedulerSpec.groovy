package com.modzo.ors.stations.services.stream.checker

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class StreamCheckerSchedulerSpec extends IntegrationSpec {

    @Autowired
    StreamCheckerSchedulerPreparer testTargetPreparer

    void 'scheduler should check not checked streams'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            StreamCheckerScheduler testTarget = testTargetPreparer.prepare(prepareStub(stream))
        when:
            testTarget.check()
        then:
            RadioStationStream checkedStream = radioStationStreams.findById(stream.id).get()
            checkedStream.checked
    }

    private FindOldestCheckedRadioStationStream.Handler prepareStub(RadioStationStream stream) {
        FindOldestCheckedRadioStationStream.Handler handler = Stub()
        handler.handle(_ as FindOldestCheckedRadioStationStream) >> Optional.of(stream)
        return handler
    }

    @Component
    private static class StreamCheckerSchedulerPreparer {

        @Autowired
        private StreamCheckService streamCheckService

        StreamCheckerScheduler prepare(FindOldestCheckedRadioStationStream.Handler handler) {
            return new StreamCheckerScheduler(
                    new StreamChecker(
                            streamCheckService,
                            handler
                    )
            )
        }
    }
}