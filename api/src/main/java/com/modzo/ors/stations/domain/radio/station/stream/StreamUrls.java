package com.modzo.ors.stations.domain.radio.station.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StreamUrls extends CrudRepository<StreamUrl, Long> {

    Page<StreamUrl> findAllByStream_id(long streamId, Pageable pageable);

}
