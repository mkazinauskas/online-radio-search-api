package com.modzo.ors.stations.domain.radio.station.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface RadioStationStreams extends CrudRepository<RadioStationStream, Long> {

    Optional<RadioStationStream> findByRadioStationIdAndId(long radioStationId, long streamId);

    Optional<RadioStationStream> findByRadioStationIdAndUrl(long radioStationId, String url);

    Page<RadioStationStream> findAllByRadioStationId(long radioStationId, Pageable pageable);

    Optional<RadioStationStream> findOneBySongsCheckedIsBeforeOrSongsCheckedIsNullOrderBySongsCheckedAsc(
            ZonedDateTime songCheckedBefore
    );
}
