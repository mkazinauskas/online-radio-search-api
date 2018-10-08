package com.mozdzo.ors.domain.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Events extends JpaRepository<Event, Long> {

    Page<Event> findAllByType(Event.Type type, Pageable pageable);
}
