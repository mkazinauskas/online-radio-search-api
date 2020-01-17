package com.modzo.ors.domain.radio.station.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RadioStationStreams extends CrudRepository<RadioStationStream, Long> {

    Optional<RadioStationStream> findByRadioStationIdAndId(long radioStationId, long streamId);

    Page<RadioStationStream> findAllByRadioStationId(long radioStationId, Pageable pageable);
}
