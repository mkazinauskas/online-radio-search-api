package com.modzo.ors.stations.services.stream.checker;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStreamCheckedTime;
import com.modzo.ors.stations.services.stream.WebPageReader;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class StreamCheckService {

    private final GetRadioStationStream.Handler getRadioStationStreamHandler;

    private final UpdateRadioStationStream.Handler updateRadioStationStreamHandler;

    private final UpdateRadioStationStreamCheckedTime.Handler updateStreamCheckedTimeHandler;

    private final WebPageReader webPageReader;

    StreamCheckService(GetRadioStationStream.Handler getRadioStationStreamHandler,
                       UpdateRadioStationStream.Handler updateRadioStationStreamHandler,
                       UpdateRadioStationStreamCheckedTime.Handler updateStreamCheckedTimeHandler,
                       WebPageReader webPageReader) {
        this.getRadioStationStreamHandler = getRadioStationStreamHandler;
        this.updateRadioStationStreamHandler = updateRadioStationStreamHandler;
        this.updateStreamCheckedTimeHandler = updateStreamCheckedTimeHandler;
        this.webPageReader = webPageReader;
    }

    public void checkIfStreamWorks(long radioStationId, long streamId) {
        RadioStationStream stream = getRadioStationStreamHandler.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );
        updateSongCheckedTime(stream);
        String url = stream.getUrl();
        Optional<WebPageReader.Response> response = webPageReader.read(url);
        var isWorkingStream = response.isPresent()
                && response
                .filter(WebPageReader.Response::hasAudioContentTypeHeader)
                .isPresent();
        if (isWorkingStream) {
            if (!stream.isWorking()) {
                this.updateStream(stream, true);
            }
        } else {
            if (stream.isWorking()) {
                this.updateStream(stream, false);
            }
        }
    }

    private void updateSongCheckedTime(RadioStationStream stream) {
        updateStreamCheckedTimeHandler.handle(new UpdateRadioStationStreamCheckedTime(
                stream.getId(), ZonedDateTime.now()
        ));
    }

    private void updateStream(RadioStationStream stream, boolean isWorking) {
        var data = from(stream)
                .setWorking(isWorking)
                .build();
        update(stream, data);
    }

    private void update(RadioStationStream stream, UpdateRadioStationStream.Data data) {
        updateRadioStationStreamHandler.handle(
                new UpdateRadioStationStream(stream.getRadioStationId(), stream.getId(), data)
        );
    }

    private UpdateRadioStationStream.DataBuilder from(RadioStationStream stream) {
        return new UpdateRadioStationStream.DataBuilder()
                .setType(stream.getType().orElse(RadioStationStream.Type.UNKNOWN))
                .setBitRate(stream.getBitRate())
                .setUrl(stream.getUrl())
                .setWorking(stream.isWorking());
    }
}
