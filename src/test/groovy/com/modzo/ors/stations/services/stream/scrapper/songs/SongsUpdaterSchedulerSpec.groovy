package com.modzo.ors.stations.services.stream.scrapper.songs

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class SongsUpdaterSchedulerSpec extends IntegrationSpec {

    @Autowired
    SongsUpdaterSchedulerPreparer testTargetPreparer

    void 'scheduler should update radio station songs from old not checked stream urls'() {
        given:
            StreamUrl streamUrl = testStreamUrl.create(StreamUrl.Type.SONGS)
        and:
            SongsUpdaterScheduler testTarget = testTargetPreparer.prepare(prepareStub(streamUrl))
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
    private static class SongsUpdaterSchedulerPreparer {

        @Autowired
        private SongsUpdaterService songsUpdaterService

        SongsUpdaterScheduler prepare(FindOldestCheckedRadioStationStreamUrl.Handler handler) {
            return new SongsUpdaterScheduler(
                    new SongsUpdater(
                            handler,
                            songsUpdaterService
                    )
            )
        }
    }
}