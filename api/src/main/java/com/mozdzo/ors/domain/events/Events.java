package com.mozdzo.ors.domain.events;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Events extends JpaRepository<Event, Long> {
}
