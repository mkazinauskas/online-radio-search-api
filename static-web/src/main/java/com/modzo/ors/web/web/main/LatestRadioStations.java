package com.modzo.ors.web.web.main;

import com.modzo.ors.web.web.ApplicationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
class LatestRadioStations {

    private final ApplicationProperties properties;

    private final RestTemplate restTemplate;


    LatestRadioStations(ApplicationProperties properties,
                        RestTemplate restTemplate
    ) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    List<Data> retrieve() {
        ResponseEntity<String> entity = restTemplate.getForEntity(properties.getApiUrl() + "/radio-stations?sort=id,desc", String.class);
        return List.of();
    }


    public static class Data {

        private final long id;

        private final String uniqueId;

        private final String title;

        public Data(long id, String uniqueId, String title) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.title = title;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getTitle() {
            return title;
        }
    }
}
