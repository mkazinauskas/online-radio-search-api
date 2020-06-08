package com.modzo.ors.statistics;

import java.util.Map;

public interface StatisticProvider {

    Type type();

    Map<Subtype, String> values();

    enum Type {
        RADIO_STATION;
    }

    enum Subtype{
        COUNT,
        ENABLED_COUNT
    }
}