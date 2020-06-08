package com.modzo.ors.statistics.provider;

import com.modzo.ors.last.searches.domain.SearchedQueries;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.GetLastSearchQueries;
import com.modzo.ors.statistics.StatisticProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;

@Component
class SearchedQueriesStatsProvider implements StatisticProvider {

    private static final Logger log = LoggerFactory.getLogger(SearchedQueriesStatsProvider.class);

    private final SearchedQueries searchedQueries;

    private final GetLastSearchQueries.Handler lastSearchQueriesHandler;

    public SearchedQueriesStatsProvider(SearchedQueries searchedQueries,
                                        GetLastSearchQueries.Handler lastSearchQueriesHandler) {
        this.searchedQueries = searchedQueries;
        this.lastSearchQueriesHandler = lastSearchQueriesHandler;
    }

    @Override
    public Type type() {
        return Type.SEARCHED_QUERIES;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(searchedQueries.count())),
                Map.entry(Subtype.LATEST_ENTRY_DATE, latestSearchQueryDate()
                )
        );
    }

    private String latestSearchQueryDate() {
        try {
            GetLastSearchQueries request = new GetLastSearchQueries(PageRequest.of(0, 1));
            return lastSearchQueriesHandler.handle(request)
                    .getContent()
                    .stream()
                    .findFirst()
                    .map(SearchedQuery::getCreated)
                    .map(ZonedDateTime::toString)
                    .orElse("none");
        } catch (Exception ex) {
            log.error("Failed to retrieve latest search query date", ex);
            return "none";
        }
    }
}
