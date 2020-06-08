package com.modzo.ors.statistics;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class StatisticsService {

    private final List<StatisticProvider> statisticProviders;

    StatisticsService(List<StatisticProvider> statisticProviders) {
        this.statisticProviders = statisticProviders;
    }

    Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> get() {
        return statisticProviders.stream()
                .collect(Collectors.toMap(StatisticProvider::type, StatisticProvider::values));
    }
}
