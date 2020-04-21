package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StreamPlayedSongsUrlGenerator extends StreamUrlGenerator {

    StreamPlayedSongsUrlGenerator(
            @Value("${application.radio.station.stream.scrapper.played.songs.url.endings}") List<String> endings) {
        super(endings);
    }

    @Override
    StreamUrl.Type forType() {
        return StreamUrl.Type.SONGS;
    }

}