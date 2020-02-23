package com.modzo.ors.web.web.api.radio.stations;

import com.modzo.ors.web.web.ApplicationProperties;
import com.modzo.ors.web.web.api.HttpEntityBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Component
public class RadioStationsClient {

    private final ApplicationProperties properties;

    private final RestTemplate restTemplate;

    public RadioStationsClient(ApplicationProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    public RadioStationsResource getStations() {
        String body = restTemplate.exchange(
                properties.getApiUrl() + "/radio-stations" + "?size=100",
                GET,
                null,
                String.class
        ).getBody();
        return restTemplate.exchange(
                properties.getApiUrl() + "/radio-stations" + "?size=100",
                GET,
                null,
                RadioStationsResource.class
        ).getBody();
    }

    public RadioStationResource getStation(long id) {
        return restTemplate.exchange(
                properties.getApiUrl() + "/radio-stations/" + id,
                GET,
                HttpEntityBuilder.builder().build(),
                RadioStationResource.class
        ).getBody();
    }
}
