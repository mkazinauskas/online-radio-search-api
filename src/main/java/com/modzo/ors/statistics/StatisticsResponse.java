package com.modzo.ors.statistics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

class StatisticsResponse {

    private final Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> statistics;

    @JsonCreator
    StatisticsResponse(
            @JsonProperty("statistics") Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> statistics
    ) {
        this.statistics = statistics;
    }

    public Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> getStatistics() {
        return statistics;
    }
}
