package com.mozdzo.ors.resources.admin.radio.station.stream.playlist;

import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation;
import com.mozdzo.ors.domain.radio.station.song.Song;
import com.mozdzo.ors.domain.radio.station.song.commands.CreateSong;
import com.mozdzo.ors.domain.radio.station.song.commands.FindSongByPlayingTime;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import com.mozdzo.ors.services.scrapper.songs.LastPlayedSongsScrapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class StreamSongsService {
    private final GetRadioStationStream.Handler radioStationStream;

    private final GetRadioStation.Handler radioStation;

    private final LastPlayedSongsScrapper playedSongsScrapper;

    private final FindSongByPlayingTime.Handler findSong;

    private final CreateSong.Handler createSong;

    public StreamSongsService(GetRadioStationStream.Handler radioStationStream,
                              GetRadioStation.Handler radioStation,
                              LastPlayedSongsScrapper playedSongsScrapper,
                              FindSongByPlayingTime.Handler findSong,
                              CreateSong.Handler createSong) {
        this.radioStationStream = radioStationStream;
        this.radioStation = radioStation;
        this.playedSongsScrapper = playedSongsScrapper;
        this.findSong = findSong;
        this.createSong = createSong;
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
        Optional<Song> foundSong = findSong.handle(new FindSongByPlayingTime(radioStationId, playedSong.getPlayedTime()));
        if (!foundSong.isPresent()) {
            createSong.handle(new CreateSong(radioStationId, playedSong.getName(), playedSong.getPlayedTime()));
        }
    }
}
