package com.modzo.ors.web.web.components;

import com.modzo.ors.web.web.components.latest.searches.LatestSearchesComponent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonComponents {

    private final LatestSearchesComponent latestSearchesComponent;

    public CommonComponents(LatestSearchesComponent latestSearchesComponent) {
        this.latestSearchesComponent = latestSearchesComponent;
    }

    public Map<String, Object> load() {
        return Map.of(
                ComponentType.LATEST_SEARCHES.getType(), latestSearchesComponent.retrieve()
        );
    }
}
