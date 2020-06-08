package com.modzo.ors.statistics;

import com.modzo.ors.stations.domain.radio.station.genre.commands.CreateGenre;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;

@RestController
class StatisticsController {

    private final StatisticsService statisticsService;

    StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/admin/statistics")
    ResponseEntity<StatisticsResponse> createGenre() {
        Map<StatisticProvider.Type, Map<StatisticProvider.Subtype, String>> statistics = statisticsService.get();
        return ResponseEntity.ok(new StatisticsResponse(statistics));
    }
}
