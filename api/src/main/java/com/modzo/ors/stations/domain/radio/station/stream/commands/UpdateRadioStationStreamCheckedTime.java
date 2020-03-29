package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamCheckedTimeUpdated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

public class UpdateRadioStationStreamCheckedTime {

    private final long streamId;

    private final ZonedDateTime checkedTime;

    public UpdateRadioStationStreamCheckedTime(long streamId, ZonedDateTime checkedTime) {
        this.streamId = streamId;
        this.checkedTime = checkedTime;
    }

    public long getStreamId() {
        return streamId;
    }

    public ZonedDateTime getCheckedTime() {
        return checkedTime;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams streams;

        private final UpdateRadioStationStreamCheckedTime.Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStationStreams streams,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.streams = streams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateRadioStationStreamCheckedTime command) {
            validator.validate(command);

            RadioStationStream stream = streams.findById(command.streamId).get();
            stream.setChecked(command.checkedTime);

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamCheckedTimeUpdated(
                            stream,
                            new RadioStationStreamCheckedTimeUpdated.Data(
                                    stream.getId(),
                                    stream.getUniqueId(),
                                    stream.getChecked()
                            )
                    )
            );
        }
    }

    @Component
    private static class Validator {

        private final RadioStationStreams streams;

        public Validator(RadioStationStreams streams) {
            this.streams = streams;
        }

        void validate(UpdateRadioStationStreamCheckedTime command) {
            if (streams.findById(command.streamId).isEmpty()) {
                throw new DomainException("FIELD_STREAM_ID_IS_INCORRECT",
                        "Radio station stream id is not available");
            }
            if (isNull(command.checkedTime)) {
                throw new DomainException(
                        "FIELD_CHECKED_TIME_IS_NULL",
                        "Field checked time should not be null"
                );
            }
        }
    }
}
