package com.mozdzo.ors.resources.admin.radio.station.stream.playlist;

import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation;
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong;
import com.mozdzo.ors.domain.radio.station.song.commands.CreateRadioStationSong;
import com.mozdzo.ors.domain.radio.station.song.commands.FindRadioStationSongByPlayingTime;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import com.mozdzo.ors.domain.song.Song;
import com.mozdzo.ors.domain.song.commands.CreateSong;
import com.mozdzo.ors.domain.song.commands.FindSong;
import com.mozdzo.ors.domain.song.commands.GetSong;
import com.mozdzo.ors.services.scrapper.songs.LastPlayedSongsScrapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class StreamSongsService {
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

    private void updateRadioStreamInfo(LastPlayedSongsScrapper.Response.PlayedSong playedSong, long radioStationId) {
        Optional<RadioStationSong> foundSong = findSong.handle(new FindRadioStationSongByPlayingTime(radioStationId, playedSong.getPlayedTime()));
        if (!foundSong.isPresent()) {
            Song song = findOrCreateSong(playedSong.getName());
            createRadioStationSong.handle(new CreateRadioStationSong(song.getId(), radioStationId, playedSong.getPlayedTime()));
        }
    }

    private Song findOrCreateSong(String title) {
        return findSongByTitle.handle(new FindSong(title)).orElseGet(() -> createSong(title));
    }

    private Song createSong(String title) {
        CreateSong.Result creationResult = createSong.handle(new CreateSong(title));
        return getSongById.handle(new GetSong(creationResult.id));
    }
}
