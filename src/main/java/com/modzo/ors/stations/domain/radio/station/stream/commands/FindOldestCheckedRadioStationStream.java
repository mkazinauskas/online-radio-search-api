package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public class FindOldestCheckedRadioStationStream {

    private final ZonedDateTime before;

    public FindOldestCheckedRadioStationStream(ZonedDateTime before) {
        this.before = before;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams streams;

        private final Validator validator;

        public Handler(RadioStationStreams streams, Validator validator) {
            this.streams = streams;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Optional<RadioStationStream> handle(FindOldestCheckedRadioStationStream command) {
            validator.validate(command);
            return streams.findTop1ByCheckedBeforeOrCheckedIsNull(command.before);
        }
    }

    @Component
    private static class Validator {

        void validate(FindOldestCheckedRadioStationStream command) {
            if (command.before == null) {
                throw new DomainException(
                        "FIELD_BEFORE_IS_NOT_SET",
                        "before",
                        "Radio station stream field before is not set");
            }
        }
    }
}
