package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.api.radio.stations.streams.RadioStationStreamResponse;
import com.modzo.ors.web.web.api.radio.stations.streams.RadioStationStreamsClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class RadioStationStreamService {
    private final RadioStationStreamsClient client;

    public RadioStationStreamService(RadioStationStreamsClient client) {
        this.client = client;
    }

    List<Data> retrieve(Long id) {
        PagedModel<EntityModel<RadioStationStreamResponse>> streams = client.getRadioStationStreams(id);
        return streams.getContent()
                .stream()
                .map(EntityModel::getContent)
                .map(response -> new Data(
                        response.getId(),
                        response.getUniqueId(),
                        response.getUrl(),
                        response.getBitRate(),
                        response.getType()
                ))
                .collect(Collectors.toList());
    }

    public static class Data {

        private final long id;

        private final String uniqueId;

        private final String url;

        private final int bitRate;

        private final String type;

        private Data(long id,
                     String uniqueId,
                     String url,
                     Integer bitRate,
                     String type) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.url = url;
            this.bitRate = bitRate;
            this.type = type;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getUrl() {
            return url;
        }

        public int getBitRate() {
            return bitRate;
        }

        public String getType() {
            return type;
        }
    }
}
