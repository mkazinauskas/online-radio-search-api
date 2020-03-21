package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StreamInfoUrlGenerator extends StreamUrlGenerator {

    StreamInfoUrlGenerator(
            @Value("${application.radio.station.stream.scrapper.info.url.endings}") List<String> endings) {
        super(endings);
    }

    @Override
    StreamUrl.Type forType() {
        return StreamUrl.Type.INFO;
    }

}