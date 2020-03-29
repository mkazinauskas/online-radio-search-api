package com.modzo.ors.stations.services.stream.scrapper.info

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class InfoUpdaterSchedulerSpec extends IntegrationSpec {

    @Autowired
    InfoUpdaterSchedulerPreparer testTargetPreparer

    void 'scheduler should check not checked streams'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create(StreamUrl.Type.INFO)
        and:
            InfoUpdaterScheduler testTarget = testTargetPreparer.prepare(prepareStub(streamUrl))
        when:
            testTarget.update()
        then:
            StreamUrl checkedStreamUrl = streamUrls.findById(streamUrl.id).get()
            checkedStreamUrl.checked
    }

    private FindOldestCheckedRadioStationStreamUrl.Handler prepareStub(StreamUrl streamUrl) {
        FindOldestCheckedRadioStationStreamUrl.Handler handler = Stub()
        handler.handle(_ as FindOldestCheckedRadioStationStreamUrl) >> Optional.of(streamUrl)
        return handler
    }

    @Component
    private static class InfoUpdaterSchedulerPreparer {

        @Autowired
        private InfoUpdaterService infoUpdaterService

        InfoUpdaterScheduler prepare(FindOldestCheckedRadioStationStreamUrl.Handler handler) {
            return new InfoUpdaterScheduler(
                    new InfoUpdater(
                            infoUpdaterService,
                            handler
                    )
            )
        }
    }
}