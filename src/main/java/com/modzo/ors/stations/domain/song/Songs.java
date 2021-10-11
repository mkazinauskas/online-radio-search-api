package com.modzo.ors.stations.domain.song;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Songs extends JpaRepository<Song, Long> {

    Optional<Song> findByTitle(String title);

    @Query("SELECT s FROM Song s WHERE ILIKE(title,:title)=true")
    Page<Song> findAllByTitleAndEnabledTrue(@Param("title") String title, Pageable pageable);

}
