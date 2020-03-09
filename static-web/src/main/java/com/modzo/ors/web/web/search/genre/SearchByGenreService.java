package com.modzo.ors.web.web.search.genre;

import com.modzo.ors.web.web.api.search.genre.SearchGenreClient;
import com.modzo.ors.web.web.api.search.genre.SearchGenreResultResponse;
import com.modzo.ors.web.web.components.common.Paged;
import com.modzo.ors.web.web.components.common.model.GenreModel;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class SearchByGenreService {

    private final SearchGenreClient searchGenreClient;

    SearchByGenreService(SearchGenreClient searchGenreClient) {
        this.searchGenreClient = searchGenreClient;
    }

    Paged<GenreModel> retrieve(String seoTitle, Pageable pageable) {
        var title = SeoText.revert(seoTitle);
        PagedModel<SearchGenreResultResponse> resultResponses = searchGenreClient.searchGenresByTitle(
                title,
                pageable.getPageNumber()
        );

        List<GenreModel> convertedGenres = resultResponses.getContent().stream()
                .map(GenreModel::new)
                .collect(Collectors.toList());

        return new Paged<>(
                convertedGenres,
                resultResponses.getMetadata()
        );
    }
}
