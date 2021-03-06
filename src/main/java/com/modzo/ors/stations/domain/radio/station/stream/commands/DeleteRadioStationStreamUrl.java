package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class DeleteRadioStationStreamUrl {

    private final long radioStationId;

    private final long streamId;

    private final long streamUrlId;

    public DeleteRadioStationStreamUrl(long radioStationId, long streamId, long streamUrlId) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.streamUrlId = streamUrlId;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams streams;

        private final Validator validator;

        Handler(RadioStationStreams streams,
                Validator validator) {
            this.streams = streams;
            this.validator = validator;
        }

        @Transactional
        public void handle(DeleteRadioStationStreamUrl command) {
            validator.validate(command);

            var stream = streams.findByRadioStation_IdAndId(command.radioStationId, command.streamId).get();
            var urlToRemove = stream.getUrls().values().stream()
                    .filter(url -> url.getId() == command.streamUrlId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Stream url was not found!"));

            stream.getUrls()
                    .remove(urlToRemove.getType());
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final RadioStationStreams radioStationStreams;

        private final StreamUrls streamUrls;

        Validator(RadioStations radioStations,
                         RadioStationStreams radioStationStreams,
                         StreamUrls streamUrls) {
            this.radioStations = radioStations;
            this.radioStationStreams = radioStationStreams;
            this.streamUrls = streamUrls;
        }

        void validate(DeleteRadioStationStreamUrl command) {


            radioStations.findById(command.radioStationId)
                    .orElseThrow(() -> radioStationWithIdDoesNotExist(command));

            radioStationStreams.findById(command.streamId)
                    .orElseThrow(() -> radioStationStreamWithIdDoesNotExist(command));

            streamUrls.findById(command.streamUrlId)
                    .orElseThrow(() -> radioStationStreamUrlWithIdDoesNotExist(command));
        }

        private DomainException radioStationWithIdDoesNotExist(DeleteRadioStationStreamUrl command) {
            return new DomainException(
                    "RADIO_STATION_WITH_ID_DOES_NOT_EXIST",
                    "radioStationId",
                    String.format("Radio station with id = `%s` does not exist", command.radioStationId)
            );
        }

        private DomainException radioStationStreamWithIdDoesNotExist(DeleteRadioStationStreamUrl command) {
            return new DomainException(
                    "RADIO_STATION_STREAM_FOR_RADIO_STATION_DOES_NOT_EXIST",
                    Set.of("streamId", "radioStationId"),
                    String.format(
                            "Radio station stream with id = `%s` does not exist for radio station with id = `%s`",
                            command.streamId,
                            command.radioStationId
                    )
            );
        }

        private DomainException radioStationStreamUrlWithIdDoesNotExist(DeleteRadioStationStreamUrl command) {
            return new DomainException(
                    "RADIO_STATION_STREAM_URL_FOR_RADIO_STATION_DOES_NOT_EXIST",
                    "streamUrlId",
                    String.format(
                            "Radio station stream url with id = `%s` does not exist "
                                    + "for radio station stream with id = `%s` "
                                    + "and radio station with id = `%s`",
                            command.streamUrlId,
                            command.streamId,
                            command.radioStationId
                    )
            );
        }
    }
}
