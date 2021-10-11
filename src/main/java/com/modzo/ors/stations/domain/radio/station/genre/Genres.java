package com.modzo.ors.stations.domain.radio.station.genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Genres extends JpaRepository<Genre, Long> {

    Optional<Genre> findByTitle(String title);

    @Query("SELECT g FROM Genre g WHERE ILIKE(title,:title)=true")
    Page<Genre> findAllByTitleAndEnabledTrue(@Param("title") String title, Pageable pageable);

}
