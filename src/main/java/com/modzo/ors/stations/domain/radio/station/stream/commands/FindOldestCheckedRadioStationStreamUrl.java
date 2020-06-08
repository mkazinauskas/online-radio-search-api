package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public class FindOldestCheckedRadioStationStreamUrl {

    private final StreamUrl.Type type;

    private final ZonedDateTime before;

    public FindOldestCheckedRadioStationStreamUrl(StreamUrl.Type type, ZonedDateTime before) {
        this.type = type;
        this.before = before;
    }

    @Component
    public static class Handler {

        private final StreamUrls streamUrls;

        private final Validator validator;

        public Handler(StreamUrls streamUrls, Validator validator) {
            this.streamUrls = streamUrls;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Optional<StreamUrl> handle(FindOldestCheckedRadioStationStreamUrl command) {
            validator.validate(command);
            return streamUrls
                    .findTop1ByTypeAndCheckedBeforeOrCheckedIsNull(command.type, command.before);
        }
    }

    @Component
    private static class Validator {

        void validate(FindOldestCheckedRadioStationStreamUrl command) {
            if (command.type == null) {
                throw new DomainException("FIELD_TYPE_IS_NOT_SET",
                        "type",
                        "Radio station stream url type is not set"
                );
            }

            if (command.before == null) {
                throw new DomainException(
                        "FIELD_BEFORE_IS_NOT_SET",
                        "title",
                        "Radio station stream url field before is not set"
                );
            }
        }
    }
}
