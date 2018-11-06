package com.mozdzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.mozdzo.ors.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class RadioStationSongsResource extends PagedResources<RadioStationSongResource> {

    @JsonCreator
    private RadioStationSongsResource(
            @JsonProperty("_embedded") Map<String, Collection<RadioStationSongResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(resolve(content), metadata, parseLinks(links));
    }

    private static Collection<RadioStationSongResource> resolve(
            Map<String, Collection<RadioStationSongResource>> content
    ) {
        return CollectionUtils.isEmpty(content)
                ? Collections.emptyList()
                : content.get("radioStationSongResourceList");
    }

    static RadioStationSongsResource create(Page<RadioStationSong> songs, long radioStationId, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<RadioStationSongResource> resources = songs.getContent()
                .stream()
                .map((RadioStationSong radioStationSong) ->
                        RadioStationSongResource.create(radioStationId, radioStationSong))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSongs(radioStationId, pageable)).withSelfRel();

        return new RadioStationSongsResource(singletonMap("radioStationSongResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel(), link)
        );
    }
}
