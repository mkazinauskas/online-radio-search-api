package com.modzo.ors.stations.domain.radio.station;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadioStations extends JpaRepository<RadioStation, Long> {

    Page<RadioStation> findAllBySongs_SongId(long songId, Pageable pagination);

    Page<RadioStation> findAllByGenres_Id(long genreId, Pageable pagination);

}
