package com.modzo.ors.statistics;

import java.util.Map;

public interface StatisticProvider {

    Type type();

    Map<Subtype, String> values();

    enum Type {
        RADIO_STATIONS,
        RADIO_STATION_STREAMS,
        EVENTS,
        PARSED_EVENTS,
        SEARCHED_QUERIES;
    }

    enum Subtype {
        COUNT,
        WORKING_COUNT,
        ENABLED_COUNT,
        LATEST_ENTRY_DATE,
        LATEST_ENTRY_ID;
    }
}