package com.modzo.ors.search.resources.song;

import com.modzo.ors.search.domain.SongDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SearchSongResultsModel extends PagedModel<SearchSongResultResponse> {

    private SearchSongResultsModel() {
    }

    private SearchSongResultsModel(Collection<SearchSongResultResponse> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static SearchSongResultsModel create(Page<SongDocument> songs, Pageable pageable, String query) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<SearchSongResultResponse> resources = songs.getContent()
                .stream()
                .map(SearchSongResultResponse::create)
                .collect(toList());

        Link link = linkTo(methodOn(SearchSongController.class)
                .search(query, pageable)).withSelfRel();

        return new SearchSongResultsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
