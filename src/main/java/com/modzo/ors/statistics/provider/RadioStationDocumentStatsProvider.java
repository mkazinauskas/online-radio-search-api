package com.modzo.ors.statistics.provider;

import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class RadioStationDocumentStatsProvider implements StatisticProvider {

    private final RadioStationsRepository repository;

    public RadioStationDocumentStatsProvider(RadioStationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Type type() {
        return Type.RADIO_STATIONS_DOCUMENTS;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(repository.count()))
        );
    }
}
