package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.events.StreamUrlCreatedEvent;
import com.modzo.ors.stations.services.stream.scrapper.songs.SongsUpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class InfoStreamUrlCreatedEventListener implements ApplicationListener<StreamUrlCreatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(InfoStreamUrlCreatedEventListener.class);

    private final InfoUpdaterService infoUpdaterService;

    private final GetRadioStationStream.Handler getRadioStationStream;

    public InfoStreamUrlCreatedEventListener(InfoUpdaterService infoUpdaterService,
                                             GetRadioStationStream.Handler getRadioStationStream) {
        this.infoUpdaterService = infoUpdaterService;
        this.getRadioStationStream = getRadioStationStream;
    }

    @Override
    public void onApplicationEvent(StreamUrlCreatedEvent event) {
        if (event.getType() != StreamUrl.Type.INFO) {
            return;
        }
        RadioStationStream stream = getRadioStationStream.handle(
                new GetRadioStationStream(event.getRadioStationId(), event.getStreamId())
        );

        Optional.ofNullable(stream.getUrls().get(event.getType()))
                .ifPresent(streamUrl -> tryUpdate(event));

    }

    private void tryUpdate(StreamUrlCreatedEvent event) {
        try {
            infoUpdaterService.update(event.getRadioStationId(), event.getStreamId());
        } catch (Exception exception) {
            log.error(
                    String.format(
                            "Failed to update stream info for radio station with id='%s', streamId='%s', type='%s'",
                            event.getRadioStationId(), event.getStreamId(), event.getType().name()
                    ), exception
            );
        }
    }
}
