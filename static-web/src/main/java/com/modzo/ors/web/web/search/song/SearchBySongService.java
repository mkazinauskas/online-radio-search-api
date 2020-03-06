package com.modzo.ors.web.web.search.song;

import com.modzo.ors.web.web.api.search.song.SearchSongClient;
import com.modzo.ors.web.web.api.search.song.SearchSongResultResponse;
import com.modzo.ors.web.web.components.common.Paged;
import com.modzo.ors.web.web.components.common.model.SongModel;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
class SearchBySongService {

    private final SearchSongClient searchSongClient;

    SearchBySongService(SearchSongClient searchSongClient) {
        this.searchSongClient = searchSongClient;
    }

    Paged<SongModel> retrieve(String seoTitle, Pageable pageable) {
        var title = SeoText.revert(seoTitle);
        PagedModel<SearchSongResultResponse> foundSongs = this.searchSongClient.searchSongsByTitle(
                title,
                pageable.getPageNumber()
        );

        List<SongModel> convertedSongs = foundSongs.getContent().stream()
                .map(SongModel::new)
                .collect(Collectors.toList());

        return new Paged<>(
                convertedSongs,
                foundSongs.getMetadata()
        );
    }
}
