package com.modzo.ors.events.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Events extends JpaRepository<Event, Long> {

    Page<Event> findAllByType(Event.Type type, Pageable pageable);

    Optional<Event> findTop1ByIdGreaterThanOrderByIdAsc(Long id);

}
