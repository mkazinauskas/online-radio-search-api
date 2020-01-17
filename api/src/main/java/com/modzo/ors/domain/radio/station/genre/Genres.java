package com.modzo.ors.domain.radio.station.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Genres extends JpaRepository<Genre, Long> {
    Optional<Genre> findByTitle(String title);
}
