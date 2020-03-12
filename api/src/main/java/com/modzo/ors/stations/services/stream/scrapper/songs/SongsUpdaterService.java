package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.commands.CreateRadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.commands.FindRadioStationSongByPlayingTime;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateSongsCheckedTime;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.commands.CreateSong;
import com.modzo.ors.stations.domain.song.commands.FindSong;
import com.modzo.ors.stations.domain.song.commands.GetSong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class SongsUpdaterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SongsUpdaterService.class);

    private final GetRadioStationStream.Handler radioStationStream;

    private final LastPlayedSongsScrapper playedSongsScrapper;

    private final FindRadioStationSongByPlayingTime.Handler findSong;

    private final CreateRadioStationSong.Handler createRadioStationSong;

    private final FindSong.Handler findSongByTitle;

    private final CreateSong.Handler createSong;

    private final GetSong.Handler getSongById;

    private final UpdateSongsCheckedTime.Handler updateSongsCheckedTimeHandler;

    public SongsUpdaterService(GetRadioStationStream.Handler radioStationStream,
                               LastPlayedSongsScrapper playedSongsScrapper,
                               FindRadioStationSongByPlayingTime.Handler findSong,
                               CreateRadioStationSong.Handler createRadioStationSong,
                               FindSong.Handler findSongByTitle,
                               CreateSong.Handler createSong,
                               GetSong.Handler getSongById,
                               UpdateSongsCheckedTime.Handler updateSongsCheckedTimeHandler) {
        this.radioStationStream = radioStationStream;
        this.playedSongsScrapper = playedSongsScrapper;
        this.findSong = findSong;
        this.createRadioStationSong = createRadioStationSong;
        this.findSongByTitle = findSongByTitle;
        this.createSong = createSong;
        this.getSongById = getSongById;
        this.updateSongsCheckedTimeHandler = updateSongsCheckedTimeHandler;
    }

    public void update(long radioStationId, long streamId) {
        RadioStationStream stream = radioStationStream.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );

        updateSongsCheckedTimeHandler.handle(new UpdateSongsCheckedTime(radioStationId, streamId, ZonedDateTime.now()));

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
