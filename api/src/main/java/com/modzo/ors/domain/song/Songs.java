package com.modzo.ors.domain.song;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Songs extends JpaRepository<Song, Long> {
    Optional<Song> findByTitle(String title);
}
