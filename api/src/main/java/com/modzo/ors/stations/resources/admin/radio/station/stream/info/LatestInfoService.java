package com.modzo.ors.stations.resources.admin.radio.station.stream.info;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.commands.CreateGenre;
import com.modzo.ors.stations.domain.radio.station.genre.commands.FindGenre;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStream;
import com.modzo.ors.stations.services.stream.scrapper.stream.StreamScrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class LatestInfoService {

    private final GetRadioStationStream.Handler radioStationStream;

    private final GetRadioStation.Handler radioStation;

    private final UpdateRadioStationStream.Handler updateRadioStationStream;

    private final UpdateRadioStation.Handler updateRadioStation;

    private final StreamScrapper streamScrapper;

    private final CreateGenre.Handler createGenre;

    private final FindGenre.Handler findGenre;

    public LatestInfoService(GetRadioStationStream.Handler radioStationStream,
                             GetRadioStation.Handler radioStation,
                             UpdateRadioStationStream.Handler updateRadioStationStream,
                             UpdateRadioStation.Handler updateRadioStation,
                             StreamScrapper streamScrapper,
                             CreateGenre.Handler createGenre, FindGenre.Handler findGenre) {
        this.radioStationStream = radioStationStream;
        this.radioStation = radioStation;
        this.updateRadioStationStream = updateRadioStationStream;
        this.updateRadioStation = updateRadioStation;
        this.streamScrapper = streamScrapper;
        this.createGenre = createGenre;
        this.findGenre = findGenre;
    }

    void update(long radioStationId, long streamId) {
        RadioStationStream stream = radioStationStream.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );

        String streamUrl = stream.getUrl();

        Optional<StreamScrapper.Response> scrappedPage = streamScrapper.scrap(
                new StreamScrapper.Request(streamUrl)
        );

        scrappedPage.ifPresent(response -> updateRadioStreamInfo(response, stream));
        scrappedPage.ifPresent(response -> updateRadioStationInfo(response, stream.getRadioStationId()));
    }

    private void updateRadioStationInfo(StreamScrapper.Response response, long radioStationId) {
        RadioStation currentRadioStation = radioStation.handle(new GetRadioStation(radioStationId));

        String radioStationName = resolveString(currentRadioStation.getTitle(), response.getStreamName());
        String website = resolveString(currentRadioStation.getWebsite(), response.getWebsite());

        Set<Genre> genres = resolveSet(currentRadioStation.getGenres(), genres(response.getGenres()));
        updateRadioStation.handle(new UpdateRadioStation(radioStationId,
                new UpdateRadioStation.Data(
                        radioStationName,
                        website,
                        genres
                )));
    }

    private Set<Genre> resolveSet(Set<Genre> previousSet, Set<Genre> newSet) {
        if (!isEmpty(previousSet) && isEmpty(newSet)) {
            return previousSet;
        }

        return newSet;
    }

    private String resolveString(String previousString, String newString) {
        if (isNotBlank(previousString) && isBlank(newString)) {
            return previousString;
        }

        return newString;
    }

    private Set<Genre> genres(List<String> genres) {
        return genres.stream()
                .map(this::findOrCreateGenre)
                .collect(toSet());
    }

    private Genre findOrCreateGenre(String genre) {
        Optional<Genre> foundGenre = findGenre.handle(new FindGenre(genre));
        if (foundGenre.isPresent()) {
            return foundGenre.get();
        } else {
            createGenre.handle(new CreateGenre(genre));
            return findGenre.handle(new FindGenre(genre)).get();
        }
    }

    private void updateRadioStreamInfo(StreamScrapper.Response response, RadioStationStream stream) {
        UpdateRadioStationStream update = new UpdateRadioStationStream(
                stream.getRadioStationId(),
                stream.getId(),
                new UpdateRadioStationStream.Data(
                        stream.getUrl(),
                        response.getBitrate(),
                        resolve(response.getFormat())
                )
        );
        updateRadioStationStream.handle(update);
    }

    private RadioStationStream.Type resolve(StreamScrapper.Response.Format format) {
        switch (format) {
            case AAC:
                return RadioStationStream.Type.ACC;
            case MP3:
                return RadioStationStream.Type.MP3;
            case UNKNOWN:
                return RadioStationStream.Type.UNKNOWN;
            default:
                throw new IllegalArgumentException(format("Failed to resolve %s", format));
        }
    }
}
