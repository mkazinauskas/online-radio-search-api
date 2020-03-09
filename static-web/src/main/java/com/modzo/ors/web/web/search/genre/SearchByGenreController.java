package com.modzo.ors.web.web.search.genre;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchByGenreController {

    private final CommonComponents commonComponents;

    private final SearchByGenreService searchByGenreService;

    public SearchByGenreController(CommonComponents commonComponents, SearchByGenreService searchByGenreService) {
        this.commonComponents = commonComponents;
        this.searchByGenreService = searchByGenreService;
    }

    @GetMapping("/search/by-genre/{query}")
    public ModelAndView searchBySong(@PathVariable("query") String query,
                                     Pageable pageable) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("seoQuery", SeoText.from(query));
        items.put("query", SeoText.revert(query));
        items.put("foundGenres", searchByGenreService.retrieve(query, pageable));
        items.put("submenu-search-by-genre", true);
        return new ModelAndView("/search/by-genre/index", items);
    }
}
