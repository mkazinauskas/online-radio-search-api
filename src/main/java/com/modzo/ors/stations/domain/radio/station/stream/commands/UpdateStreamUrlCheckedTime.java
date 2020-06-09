package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

public class UpdateStreamUrlCheckedTime {

    private final long urlId;

    private final ZonedDateTime checkedTime;

    public UpdateStreamUrlCheckedTime(long urlId, ZonedDateTime checkedTime) {
        this.urlId = urlId;
        this.checkedTime = checkedTime;
    }

    @Component
    public static class Handler {

        private final StreamUrls streamUrls;

        private final UpdateStreamUrlCheckedTime.Validator validator;

        public Handler(StreamUrls streamUrls,
                       Validator validator) {
            this.streamUrls = streamUrls;
            this.validator = validator;
        }

        @Transactional
        public void handle(UpdateStreamUrlCheckedTime command) {
            validator.validate(command);

            StreamUrl url = streamUrls.findById(command.urlId).get();
            url.setChecked(command.checkedTime);
        }
    }

    @Component
    private static class Validator {

        private final StreamUrls streamUrls;

        public Validator(StreamUrls streamUrls) {
            this.streamUrls = streamUrls;
        }

        void validate(UpdateStreamUrlCheckedTime command) {
            if (streamUrls.findById(command.urlId).isEmpty()) {
                throw new DomainException(
                        "FIELD_URL_ID_IS_INCORRECT",
                        "urlId",
                        "Radio station stream URL id is not available"
                );
            }
            if (isNull(command.checkedTime)) {
                throw new DomainException(
                        "FIELD_CHECKED_TIME_IS_NULL",
                        "checkedTime",
                        "Field checked time should not be null"
                );
            }
        }
    }
}
