package com.modzo.ors.web.web.search.radio.station.by.played.song;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchRadioStationByPlayedSongController {

    private final CommonComponents commonComponents;

    private final SearchBySongService searchBySongService;

    public SearchRadioStationByPlayedSongController(CommonComponents commonComponents, SearchBySongService searchBySongService) {
        this.commonComponents = commonComponents;
        this.searchBySongService = searchBySongService;
    }

    @GetMapping("/radio-station/by-played-song/{songId}")
    public ModelAndView searchBySong(@PathVariable("songId") long songId) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
//        items.put("seoQuery", SeoText.from(query));
//        items.put("query", SeoText.revert(query));
//        items.put("foundSongs", searchBySongService.retrieve(query));
        items.put("submenu-search-by-song", true);
        return new ModelAndView("/search/by-song/index", items);
    }
}
