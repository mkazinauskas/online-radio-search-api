package com.modzo.ors.web.search.genre.radio.stations;

import com.modzo.ors.web.components.CommonComponents;
import com.modzo.ors.web.components.ComponentType;
import com.modzo.ors.web.components.common.Paged;
import com.modzo.ors.web.components.common.model.RadioStationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.pathEquals;

@Controller
public class RadioStationsByGenreController {

    private final CommonComponents commonComponents;

    private final RadioStationByGenreService radioStationByGenreService;

    private final GenreService genreService;

    public RadioStationsByGenreController(CommonComponents commonComponents,
                                          RadioStationByGenreService radioStationByGenreService,
                                          GenreService genreService) {
        this.commonComponents = commonComponents;
        this.radioStationByGenreService = radioStationByGenreService;
        this.genreService = genreService;
    }

    @GetMapping("/radio-stations/by-genre/{genreTitle}/{genreId}")
    public ModelAndView searchBySong(
            @PathVariable("genreTitle") String songTitle,
            @PathVariable("genreId") long genreId,
            Pageable pageable) {
        var genre = genreService.retrieveGenre(genreId);

        if (!pathEquals(genre.getSeoTitle(), songTitle)) {
            throw new IllegalArgumentException("Song not found...");
        }

        Paged<RadioStationModel> radioStations = radioStationByGenreService.retrieveStationBy(genreId, pageable);

        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("genre", genre);
        items.put("radioStations", radioStations);
        return new ModelAndView("/search/by-genre/radio-stations", items);
    }
}
