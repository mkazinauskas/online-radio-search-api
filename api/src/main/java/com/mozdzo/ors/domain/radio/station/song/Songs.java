package com.mozdzo.ors.domain.radio.station.song;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Songs extends JpaRepository<Song, Long> {
    Optional<Song> findByRadioStationIdAndId(long radioStationId, long streamId);

    Page<Song> findAllByRadioStationId(long radioStationId, Pageable pageable);
}
