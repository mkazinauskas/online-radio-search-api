package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.commons.Urls;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import com.modzo.ors.stations.events.StreamUrlCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

public class CreateRadioStationStreamUrl {

    private final long radioStationId;

    private final long streamId;

    private final StreamUrl.Type type;

    private final String url;

    public CreateRadioStationStreamUrl(
            long radioStationId,
            long streamId,
            StreamUrl.Type type,
            String url) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.type = type;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        private final StreamUrls streamUrls;

        private final ApplicationEventPublisher publisher;

        public Handler(RadioStationStreams radioStationStreams,
                       Validator validator,
                       StreamUrls streamUrls, ApplicationEventPublisher publisher) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.streamUrls = streamUrls;
            this.publisher = publisher;
        }

        @Transactional
        public CreateRadioStationStreamUrl.Result handle(CreateRadioStationStreamUrl command) {
            validator.validate(command);

            var radioStationStream = radioStationStreams.findByRadioStation_IdAndId(
                    command.radioStationId,
                    command.streamId
            ).get();

            var streamUrl = new StreamUrl(command.type, command.url);
            streamUrl.setStream(radioStationStream);

            StreamUrl savedUrl = streamUrls.save(streamUrl);

            radioStationStream.getUrls().put(command.type, savedUrl);

            publisher.publishEvent(
                    new StreamUrlCreatedEvent(
                            this, command.type, radioStationStream.getRadioStationId(), command.streamId
                    )
            );

            return new CreateRadioStationStreamUrl.Result(savedUrl.getId());
        }
    }


    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final RadioStationStreams radioStationStreams;

        Validator(RadioStations radioStations, RadioStationStreams radioStationStreams) {
            this.radioStations = radioStations;
            this.radioStationStreams = radioStationStreams;
        }

        void validate(CreateRadioStationStreamUrl command) {

            if (isNull(command.type)) {
                throw new DomainException(
                        "FIELD_TYPE_CANNOT_BE_NULL",
                        "type",
                        String.format(
                                "Radio station stream with id = `%s` for radio station with id = `%s` "
                                        + "type cannot be null",
                                command.streamId,
                                command.radioStationId
                        )
                );
            }

            if (Urls.isNotValid(command.url)) {
                throw new DomainException(
                        "FIELD_URL_IS_NOT_VALID",
                        "url",
                        String.format(
                                "Radio station stream with id = `%s` for radio station with id = `%s` url is not valid",
                                command.streamId,
                                command.radioStationId
                        )
                );
            }

            radioStations.findById(command.radioStationId)
                    .orElseThrow(() -> radioStationWithIdDoesNotExist(command));

            radioStationStreams.findById(command.streamId)
                    .orElseThrow(() -> radioStationStreamWithIdDoesNotExist(command));
        }

        private DomainException radioStationWithIdDoesNotExist(CreateRadioStationStreamUrl command) {
            return new DomainException(
                    "RADIO_STATION_WITH_ID_DOES_NOT_EXIST",
                    "radioStationId",
                    String.format("Radio station with id = `%s` does not exist", command.radioStationId)
            );
        }

        private DomainException radioStationStreamWithIdDoesNotExist(CreateRadioStationStreamUrl command) {
            return new DomainException(
                    "RADIO_STATION_STREAM_FOR_RADIO_STATION_DOES_NOT_EXIST",
                    "streamId",
                    String.format(
                            "Radio station stream with id = `%s` does not exist for radio station with id = `%s`",
                            command.streamId,
                            command.radioStationId
                    )
            );
        }
    }

    public static class Result {
        public final long id;

        Result(long id) {
            this.id = id;
        }
    }
}
