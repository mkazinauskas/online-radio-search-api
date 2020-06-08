package com.modzo.ors.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private final List<StatisticProvider> statisticProviders;

    StatisticsService(List<StatisticProvider> statisticProviders) {
        this.statisticProviders = statisticProviders;
    }

    Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> get() {
        return statisticProviders.stream()
                .collect(Collectors.toMap(StatisticProvider::type, this::retrieve));
    }

    private Map<StatisticProvider.Subtype, String> retrieve(StatisticProvider provider) {
        try {
            return provider.values();
        } catch (Exception exception) {
            log.error(String.format("Failed to retrieve statistics of '%s'", provider.type()), exception);
            return Map.of();
        }
    }
}
