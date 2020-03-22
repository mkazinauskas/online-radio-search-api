package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamDeleted;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class DeleteRadioStationStream {

    private final long radioStationId;

    private final long streamId;

    public DeleteRadioStationStream(long radioStationId, long streamId) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        Handler(RadioStations radioStations,
                RadioStationStreams radioStationStreams, Validator validator,
                ApplicationEventPublisher applicationEventPublisher) {
            this.radioStations = radioStations;
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(DeleteRadioStationStream command) {
            validator.validate(command);

            var radioStation = radioStations.findById(command.radioStationId).get();

            var radioStationStream = radioStationStreams.findByRadioStation_IdAndId(
                    command.radioStationId,
                    command.streamId
            ).get();

            radioStationStreams.delete(radioStationStream);


            applicationEventPublisher.publishEvent(
                    new RadioStationStreamDeleted(
                            radioStationStream,
                            new RadioStationStreamDeleted.Data(
                                    radioStationStream.getId(),
                                    radioStationStream.getUniqueId(),
                                    radioStation.getId(),
                                    radioStation.getUniqueId()
                            )
                    )
            );
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        private final RadioStationStreams radioStationStreams;

        public Validator(RadioStations radioStations, RadioStationStreams radioStationStreams) {
            this.radioStations = radioStations;
            this.radioStationStreams = radioStationStreams;
        }

        void validate(DeleteRadioStationStream command) {
            radioStations.findById(command.radioStationId)
                    .orElseThrow(() -> radioStationWithIdDoesNotExist(command));

            radioStationStreams.findById(command.streamId)
                    .orElseThrow(() -> radioStationStreamWithIdDoesNotExist(command));
        }

        private DomainException radioStationWithIdDoesNotExist(DeleteRadioStationStream command) {
            return new DomainException(
                    "RADIO_STATION_WITH_ID_DOES_NOT_EXIST",
                    String.format("Radio station with id = `%s` does not exist", command.radioStationId)
            );
        }

        private DomainException radioStationStreamWithIdDoesNotExist(DeleteRadioStationStream command) {
            return new DomainException(
                    "RADIO_STATION_STREAM_FOR_RADIO_STATION_DOES_NOT_EXIST",
                    String.format(
                            "Radio station stream with id = `%s` does not exist for radio station with id = `%s`",
                            command.streamId,
                            command.radioStationId
                    )
            );
        }
    }
}
