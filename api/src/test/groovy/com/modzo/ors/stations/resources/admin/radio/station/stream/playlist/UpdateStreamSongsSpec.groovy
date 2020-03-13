package com.modzo.ors.stations.resources.admin.radio.station.stream.playlist

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong
import com.modzo.ors.stations.domain.radio.station.song.commands.GetRadioStationSongs
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.domain.song.commands.GetSong
import com.modzo.ors.stations.resources.IntegrationSpec
import org.eclipse.jetty.http.HttpHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.data.domain.Pageable.unpaged
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.NO_CONTENT

class UpdateStreamSongsSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetSong.Handler getSongHandler

    @Autowired
    GetRadioStationSongs.Handler radioStationSongsHandler

    void 'admin should update latest radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            serverResponseExist(stream.url)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/streams/${stream.id}/songs",
                    POST,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == NO_CONTENT
        and:
            Page<RadioStationSong> radioStationSongs = radioStationSongsHandler.handle(
                    new GetRadioStationSongs(radioStation.id, unpaged())
            )

            radioStationSongs.totalElements == 20

            List<Song> songs = radioStationSongs.content.collect { getSongHandler.handle(new GetSong(it.songId)) }
            songs.size() == 20

            songs.find { it.title == 'Advert Trigger:  Studio Associato CF&C' }
            songs.find { it.title == 'Notiziario nazionale - Sigla finale' }
            songs.find { it.title == 'Notiziario nazionale' }
            songs.find { it.title == "Rihanna - If It's Lovin' That You Want" }
        and:
            radioStationStreamHandler.handle(new GetRadioStationStream(radioStation.id, stream.id)).songsChecked
    }

    private void serverResponseExist(String url) {
        String body = getClass().getResource('/services/scrappers/played/played-source.html').text
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): 'text/html']
        wireMockTestHelper.okGetResponse(url, headers, body)
    }
}
