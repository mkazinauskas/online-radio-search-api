package com.modzo.ors.helpers

import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.commands.CreateRadioStation
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestRadioStation {

    @Autowired
    private CreateRadioStation.Handler createRadioStationHandler

    @Autowired
    private GetRadioStation.Handler getRadioStationHandler

    @Autowired
    private UpdateRadioStation.Handler updateRadioStationHandler

    RadioStation create() {
        CreateRadioStation createRadioStation = new CreateRadioStation(randomWebsite())
        long newStationId = createRadioStationHandler.handle(createRadioStation).id
        return getRadioStationHandler.handle(new GetRadioStation(newStationId))
    }

    RadioStation create(Genre genre) {
        RadioStation station = create()
        updateRadioStationHandler.handle(
                new UpdateRadioStation(
                        station.id,
                        new UpdateRadioStation.DataBuilder()
                                .setTitle(station.title)
                                .setWebsite(station.website)
                                .setEnabled(true)
                                .setGenres([genre] as Set)
                                .build()
                )
        )
        return station
    }

    static String randomWebsite() {
        randomAlphanumeric(100)
    }

    static String randomTitle() {
        randomAlphanumeric(40)
    }
}
