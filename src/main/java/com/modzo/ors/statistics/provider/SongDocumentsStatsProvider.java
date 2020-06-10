package com.modzo.ors.statistics.provider;

import com.modzo.ors.search.domain.SongsRepository;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class SongDocumentsStatsProvider implements StatisticProvider {

    private final SongsRepository repository;

    public SongDocumentsStatsProvider(SongsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Type type() {
        return Type.SONGS_DOCUMENTS;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(repository.count()))
        );
    }
}
