package com.modzo.ors.statistics;

import java.util.Map;

public interface StatisticProvider {

    Type type();

    Map<Subtype, String> values();

    enum Type {
        RADIO_STATIONS,
        RADIO_STATIONS_DOCUMENTS,
        RADIO_STATION_STREAMS,
        GENRES_DOCUMENTS,
        SONGS_DOCUMENTS,
        SEARCHED_QUERIES;
    }

    enum Subtype {
        COUNT,
        WORKING_COUNT,
        ENABLED_COUNT,
        LATEST_ENTRY_DATE;
    }
}