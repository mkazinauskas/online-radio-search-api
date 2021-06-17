package com.modzo.ors.stations.domain.radio.station;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RadioStations extends JpaRepository<RadioStation, Long>, JpaSpecificationExecutor {

    Optional<RadioStation> findByTitle(String title);

    Optional<RadioStation> findByUniqueId(UUID uniqueId);

    Long countAllByEnabledTrue();

    Page<RadioStation> findAllByTitleAndEnabledTrue(String title, Pageable pageable);

}
