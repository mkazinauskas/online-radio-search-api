package com.modzo.ors.search.resources.genre;

import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.RadioStationDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SearchGenreResultsModel extends PagedModel<SearchGenreResultResponse> {

    private SearchGenreResultsModel() {
    }

    private SearchGenreResultsModel(
            Collection<SearchGenreResultResponse> content,
            PageMetadata metadata,
            Link... links) {
        super(content, metadata, links);
    }

    static SearchGenreResultsModel create(
            Page<GenreDocument> genres,
            Pageable pageable,
            String query) {
        PageMetadata pageMetadata = new PageMetadata(
                genres.getSize(),
                genres.getNumber(),
                genres.getTotalElements(),
                genres.getTotalPages()
        );
        Collection<SearchGenreResultResponse> resources = genres.getContent()
                .stream()
                .map(SearchGenreResultResponse::create)
                .collect(toList());

        Link link = linkTo(methodOn(SearchGenreController.class)
                .search(query, pageable)).withSelfRel();

        return new SearchGenreResultsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
