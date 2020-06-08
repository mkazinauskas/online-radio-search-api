package com.modzo.ors.statistics.provider;

import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class RadioStationsStatsProvider implements StatisticProvider {

    private final RadioStations radioStations;

    public RadioStationsStatsProvider(RadioStations radioStations) {
        this.radioStations = radioStations;
    }

    @Override
    public Type type() {
        return Type.RADIO_STATION;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(radioStations.count())),
                Map.entry(Subtype.ENABLED_COUNT, String.valueOf(radioStations.countAllByEnabledTrue()))
        );
    }
}
