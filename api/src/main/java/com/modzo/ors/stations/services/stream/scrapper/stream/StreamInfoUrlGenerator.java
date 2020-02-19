package com.modzo.ors.stations.services.stream.scrapper.stream;

import com.modzo.ors.stations.services.stream.scrapper.StreamUrlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StreamInfoUrlGenerator extends StreamUrlGenerator {

    public StreamInfoUrlGenerator(
            @Value("${application.radio.station.stream.scrapper.info.url.endings}") List<String> endings) {
        super(endings);
    }

}