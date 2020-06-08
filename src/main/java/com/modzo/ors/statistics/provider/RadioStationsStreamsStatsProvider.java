package com.modzo.ors.statistics.provider;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class RadioStationsStreamsStatsProvider implements StatisticProvider {

    private final RadioStationStreams streams;

    RadioStationsStreamsStatsProvider(RadioStationStreams streams) {
        this.streams = streams;
    }

    @Override
    public Type type() {
        return Type.RADIO_STATION_STREAMS;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(streams.count())),
                Map.entry(Subtype.WORKING_COUNT, String.valueOf(streams.countAllByWorkingTrue()))
        );
    }
}
