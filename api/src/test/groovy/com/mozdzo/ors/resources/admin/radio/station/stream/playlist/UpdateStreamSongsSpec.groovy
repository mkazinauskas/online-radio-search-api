package com.mozdzo.ors.resources.admin.radio.station.stream.playlist

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong
import com.mozdzo.ors.domain.radio.station.song.commands.GetRadioStationSongs
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.domain.song.commands.GetSong
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.mozdzo.ors.TestUsers.ADMIN
import static com.mozdzo.ors.TokenProvider.token
import static org.springframework.data.domain.Pageable.unpaged
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.NO_CONTENT

class UpdateStreamSongsSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetRadioStation.Handler radioStationHandler

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

            List<Song> songs = radioStationSongs.content.collect { getSongHandler.handle(new GetSong(it.id)) }
            songs.size() == 20
            songs.find { it.title == 'Advert Trigger:  Studio Associato CF&C' }
            songs.find { it.title == 'Notiziario nazionale - Sigla finale' }
            songs.find { it.title == 'Notiziario nazionale' }
            songs.find { it.title == "Rihanna - If It's Lovin' That You Want" }
    }

    private void serverResponseExist(String url) {
        String page = getClass().getResource('/services/scrappers/played/played-source.html').text

        testWiremockServer.server().stubFor(
                get(urlEqualTo('/' + url.split('/').last()))
                        .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(page))
        )
    }
}
