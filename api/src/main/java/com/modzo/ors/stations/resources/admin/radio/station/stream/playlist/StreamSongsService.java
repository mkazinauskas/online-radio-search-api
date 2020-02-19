package com.modzo.ors.stations.resources.admin.radio.station.stream.playlist;

import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.commands.CreateRadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.commands.FindRadioStationSongByPlayingTime;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.commands.CreateSong;
import com.modzo.ors.stations.domain.song.commands.FindSong;
import com.modzo.ors.stations.domain.song.commands.GetSong;
import com.modzo.ors.services.scrapper.songs.LastPlayedSongsScrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class StreamSongsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamSongsService.class);

    private final GetRadioStationStream.Handler radioStationStream;

    private final GetRadioStation.Handler radioStation;

    private final LastPlayedSongsScrapper playedSongsScrapper;

    private final FindRadioStationSongByPlayingTime.Handler findSong;

    private final CreateRadioStationSong.Handler createRadioStationSong;

    private final CreateRadioStationSong.Handler getCreateRadioStationSong;

    private final FindSong.Handler findSongByTitle;

    private final CreateSong.Handler createSong;

    private final GetSong.Handler getSongById;

    public StreamSongsService(GetRadioStationStream.Handler radioStationStream,
                              GetRadioStation.Handler radioStation,
                              LastPlayedSongsScrapper playedSongsScrapper,
                              FindRadioStationSongByPlayingTime.Handler findSong,
                              CreateRadioStationSong.Handler createRadioStationSong,
                              CreateRadioStationSong.Handler getCreateRadioStationSong,
                              FindSong.Handler findSongByTitle,
                              CreateSong.Handler createSong,
                              GetSong.Handler getSongById) {
        this.radioStationStream = radioStationStream;
        this.radioStation = radioStation;
        this.playedSongsScrapper = playedSongsScrapper;
        this.findSong = findSong;
        this.createRadioStationSong = createRadioStationSong;
        this.getCreateRadioStationSong = getCreateRadioStationSong;
        this.findSongByTitle = findSongByTitle;
        this.createSong = createSong;
        this.getSongById = getSongById;
    }

    void update(long radioStationId, long streamId) {
        RadioStationStream stream = radioStationStream.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );

        String streamUrl = stream.getUrl();

        Optional<LastPlayedSongsScrapper.Response> scrappedPage = playedSongsScrapper.scrap(
                new LastPlayedSongsScrapper.Request(streamUrl)
        );

        scrappedPage
                .map(LastPlayedSongsScrapper.Response::getSongs)
                .ifPresent(songs -> songs
                        .forEach(playedSong -> updateRadioStreamInfo(playedSong, radioStationId)));
    }

    private void updateRadioStreamInfo(LastPlayedSongsScrapper.Response.PlayedSong playedSong,
                                       long radioStationId) {
        Optional<RadioStationSong> foundSong = findSong.handle(
                new FindRadioStationSongByPlayingTime(radioStationId, playedSong.getPlayedTime())
        );
        if (foundSong.isEmpty()) {
            Optional<Song> song = findOrCreateSong(playedSong.getName());
            song.ifPresent(existingSong -> {
                createRadioStationSong.handle(
                        new CreateRadioStationSong(existingSong.getId(), radioStationId, playedSong.getPlayedTime())
                );
            });
        }
    }

    private Optional<Song> findOrCreateSong(String title) {
        Optional<Song> foundSong = findSongByTitle.handle(new FindSong(title));
        if (foundSong.isEmpty()) {
            return createSong(title);
        }
        return foundSong;
    }

    private Optional<Song> createSong(String title) {
        try {
            CreateSong.Result creationResult = createSong.handle(new CreateSong(title));
            getSongById.handle(new GetSong(creationResult.id));
        } catch (Exception exception) {
            LOGGER.warn("Failed to created song, but recovered and trying to search for existing", exception);
        }
        return findSongByTitle.handle(new FindSong(title));
    }
}
