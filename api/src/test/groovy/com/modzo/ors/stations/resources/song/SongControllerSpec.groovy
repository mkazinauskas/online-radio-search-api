package com.modzo.ors.stations.resources.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SongControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve songs'() {
        given:
            Song song = testSong.create()
        and:
            String url = '/songs'
        when:
            ResponseEntity<SongsModel> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SongsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as SongsModel) {
                SongModel resource = it.content.find { it.content.id == song.id }

                with(resource.content) {
                    uniqueId == song.uniqueId
                    created.toInstant() == song.created.toInstant()
                    title == song.title
                }

                with(resource.links.first()) {
                    rel == SELF
                    href.endsWith("${url}/${song.id}")
                }

                with(links.first()) {
                    rel == SELF
                    href.endsWith(url)
                }
            }
    }

    void 'anyone should retrieve song'() {
        given:
            Song song = testSong.create()
        and:
            String url = "/songs/${song.id}"
        when:
            ResponseEntity<SongModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    SongModel
            )
        then:
            result.statusCode == OK
        and:
            SongModel model = result.body
            with(model.content) {
                it.id == song.id
                it.title == song.title
            }
            with(model.links.first()) {
                rel == SELF
                href.endsWith(url)
            }
    }
}
