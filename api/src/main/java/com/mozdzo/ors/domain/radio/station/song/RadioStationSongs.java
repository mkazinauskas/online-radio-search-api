package com.mozdzo.ors.domain.radio.station.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface RadioStationSongs extends JpaRepository<RadioStationSong, Long> {
    Optional<RadioStationSong> findByRadioStationIdAndPlayedTime(long radioStationId, ZonedDateTime playedTime);

    Optional<RadioStationSong> findByRadioStationIdAndId(long radioStationId, long songId);

    Page<RadioStationSong> findAllByRadioStationId(long radioStationId, Pageable pageable);
}
