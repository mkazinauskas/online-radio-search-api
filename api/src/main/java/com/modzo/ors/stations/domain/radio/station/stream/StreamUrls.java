package com.modzo.ors.stations.domain.radio.station.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import java.time.ZonedDateTime;

public interface StreamUrls extends CrudRepository<StreamUrl, Long> {

    Page<StreamUrl> findAllByStream_id(long streamId, Pageable pageable);

    Optional<StreamUrl> findTop1ByTypeAndCheckedBeforeOrCheckedIsNull(StreamUrl.Type type, ZonedDateTime checked);

}
