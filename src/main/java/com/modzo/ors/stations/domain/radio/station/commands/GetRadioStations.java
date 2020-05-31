package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class GetRadioStations {

    public static class Filter {

        private final Long id;

        private final String uniqueId;

        private final Boolean enabled;

        private final String title;

        private final Long songId;

        private final Long genreId;

        public Filter(Long id, String uniqueId, Boolean enabled, String title, Long songId, Long genreId) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.enabled = enabled;
            this.title = title;
            this.songId = songId;
            this.genreId = genreId;
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
                    .map(this::specification)
                    .map(specification -> radioStations.findAll(specification, command.pageable))
                    .orElseGet(() -> radioStations.findAll(command.pageable));
        }

        private Specification<RadioStation> specification(Filter filter) {
            return new SpecificationBuilder()
                    .withId(filter.id)
                    .withUniqueId(filter.uniqueId)
                    .withEnabled(filter.enabled)
                    .withTitle(filter.title)
                    .withSongId(filter.songId)
                    .withGenreId(filter.genreId)
                    .build();
        }

        private static class SpecificationBuilder {

            private final List<Specification<RadioStation>> specifications = new ArrayList<>();

            SpecificationBuilder withId(Long id) {
                if (!Objects.isNull(id)) {
                    specifications.add((root, query, cb) -> cb.equal(root.get("id"), id));
                }
                return this;
            }

            SpecificationBuilder withUniqueId(String uniqueId) {
                if (!Objects.isNull(uniqueId)) {
                    specifications.add((root, query, cb) -> cb.equal(root.get("uniqueId"), uniqueId));
                }
                return this;
            }

            SpecificationBuilder withEnabled(Boolean enabled) {
                if (!Objects.isNull(enabled)) {
                    specifications.add((root, query, cb) -> cb.equal(root.get("enabled"), enabled));
                }
                return this;
            }

            SpecificationBuilder withTitle(String title) {
                if (!Objects.isNull(title)) {
                    specifications.add((root, query, cb) -> cb.like(root.get("title"), title));
                }
                return this;
            }

            SpecificationBuilder withSongId(Long songId) {
                if (!Objects.isNull(songId)) {
                    specifications.add((root, query, cb) ->
                            cb.equal(root.join("songs", JoinType.LEFT).join("song", JoinType.LEFT)
                                    .get("id"), songId)
                    );
                }
                return this;
            }

            SpecificationBuilder withGenreId(Long genreId) {
                if (!Objects.isNull(genreId)) {
                    specifications.add((root, query, cb) -> cb.equal(
                            root
                                    .join("genres", JoinType.LEFT)
                                    .get("id"), genreId
                            )
                    );
                }
                return this;
            }

            Specification<RadioStation> build() {
                return specifications.stream()
                        .reduce(Specification::and)
                        .orElse(null);
            }

        }
    }
}
