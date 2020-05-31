package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class GetRadioStations {

    public static class Filter {

        private final Long id;

        private final String uniqueId;

        private final Boolean enabled;

        private final String title;

        public Filter(Long id, String uniqueId, Boolean enabled, String title) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.enabled = enabled;
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public String getTitle() {
            return title;
        }
    }

    private final Filter filter;

    private final Pageable pageable;

    public GetRadioStations(Filter filter, Pageable pageable) {
        this.filter = filter;
        this.pageable = pageable;
    }

    public GetRadioStations(Pageable pageable) {
        this.filter = null;
        this.pageable = requireNonNull(pageable);
    }

    public Optional<Filter> getFilter() {
        return Optional.ofNullable(filter);
    }

    public Pageable getPageable() {
        return pageable;
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        public Handler(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        public Page<RadioStation> handle(GetRadioStations command) {
            return command.getFilter()
                    .map(this::toExample)
                    .map(example -> radioStations.findAll(example, command.pageable))
                    .orElseGet(() -> radioStations.findAll(command.pageable));
        }

        private Example<RadioStation> toExample(Filter filter) {
            return new RadioStation.ExampleBuilder()
                    .withId(filter.id)
                    .withUniqueId(filter.uniqueId)
                    .withEnabled(filter.enabled)
                    .withTitle(filter.title)
                    .build();
        }
    }
}
