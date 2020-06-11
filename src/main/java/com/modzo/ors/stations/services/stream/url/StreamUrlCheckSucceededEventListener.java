package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.events.StreamUrlCheckSucceededEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
class StreamUrlCheckSucceededEventListener implements ApplicationListener<StreamUrlCheckSucceededEvent> {

    private static final Logger log = LoggerFactory.getLogger(StreamUrlCheckSucceededEventListener.class);

    private final UrlResolver urlResolver;

    private final GetRadioStationStream.Handler getRadioStationStream;

    public StreamUrlCheckSucceededEventListener(UrlResolver urlResolver,
                                                GetRadioStationStream.Handler getRadioStationStream) {
        this.urlResolver = urlResolver;
        this.getRadioStationStream = getRadioStationStream;
    }

    @Override
    public void onApplicationEvent(StreamUrlCheckSucceededEvent event) {
        RadioStationStream stream = getRadioStationStream.handle(
                new GetRadioStationStream(event.getRadioStationId(), event.getStreamId())
        );

        Map<StreamUrl.Type, StreamUrl> urls = stream.getUrls();

        Arrays.stream(StreamUrl.Type.values())
                .filter(type -> !urls.containsKey(type))
                .forEach(type -> resolveByType(event, type));
    }

    private void resolveByType(StreamUrlCheckSucceededEvent event, StreamUrl.Type type) {
        try {
            urlResolver.resolve(event.getRadioStationId(), event.getStreamId(), type);
        } catch (Exception exception) {
            log.error(
                    String.format(
                            "Failed to resolve url for radio station with id='%s', streamId='%s', type='%s'",
                            event.getRadioStationId(), event.getStreamId(), type.name()
                    ), exception
            );
        }
    }
}
