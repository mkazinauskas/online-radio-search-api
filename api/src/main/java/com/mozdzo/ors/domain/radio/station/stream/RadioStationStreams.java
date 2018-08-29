package com.mozdzo.ors.domain.radio.station.stream;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RadioStationStreams extends CrudRepository<RadioStationStream, Long> {

    Optional<RadioStationStream> findByRadioStationIdAndId(long radioStationId, long streamId);
}
