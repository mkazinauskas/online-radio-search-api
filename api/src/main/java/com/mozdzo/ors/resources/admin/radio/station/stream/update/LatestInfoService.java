package com.mozdzo.ors.resources.admin.radio.station.stream.update;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.UpdateRadioStationStream;
import com.mozdzo.ors.services.scrapper.stream.StreamScrapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
public class LatestInfoService {
    private final GetRadioStationStream.Handler radioStationStream;

    private final UpdateRadioStationStream.Handler updateRadioStationStream;

    private final StreamScrapper streamScrapper;

    public LatestInfoService(GetRadioStationStream.Handler radioStationStream,
                             UpdateRadioStationStream.Handler updateRadioStationStream,
                             StreamScrapper streamScrapper) {
        this.radioStationStream = radioStationStream;
        this.updateRadioStationStream = updateRadioStationStream;
        this.streamScrapper = streamScrapper;
    }

    void update(long radioStationId, long streamId) {
        RadioStationStream stream = radioStationStream.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );

        String streamUrl = stream.getUrl();

        Optional<StreamScrapper.Response> scrappedPage = streamScrapper.scrap(
                new StreamScrapper.Request(streamUrl)
        );

        scrappedPage.ifPresent(response -> update(response, stream));
    }

    private void update(StreamScrapper.Response response, RadioStationStream stream) {
        UpdateRadioStationStream update = new UpdateRadioStationStream(
                stream.getRadioStationId(),
                stream.getId(),
                new UpdateRadioStationStream.Data(
                        stream.getUrl(),
                        response.getBitrate(),
                        resolve(response.getFormat())
                )
        );
        updateRadioStationStream.handle(update);
    }

    private RadioStationStream.Type resolve(StreamScrapper.Response.Format format) {
        switch (format) {
            case AAC:
                return RadioStationStream.Type.ACC;
            case MP3:
                return RadioStationStream.Type.MP3;
            case UNKNOWN:
                return RadioStationStream.Type.UNKNOWN;
            default:
                throw new IllegalArgumentException(format("Failed to resolve %s", format));
        }
    }
}
