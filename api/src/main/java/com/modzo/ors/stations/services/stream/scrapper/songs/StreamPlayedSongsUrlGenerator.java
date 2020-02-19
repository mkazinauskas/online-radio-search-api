package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.services.stream.scrapper.StreamUrlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StreamPlayedSongsUrlGenerator extends StreamUrlGenerator {

    public StreamPlayedSongsUrlGenerator(
            @Value("${application.radio.station.stream.scrapper.played.songs.url.endings}") List<String> endings) {
        super(endings);
    }

}