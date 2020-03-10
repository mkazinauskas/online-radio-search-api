package com.modzo.ors.web.search.song;

import com.modzo.ors.web.components.CommonComponents;
import com.modzo.ors.web.components.ComponentType;
import com.modzo.ors.web.utils.SeoText;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchBySongController {

    private final CommonComponents commonComponents;

    private final SearchBySongService searchBySongService;

    public SearchBySongController(CommonComponents commonComponents, SearchBySongService searchBySongService) {
        this.commonComponents = commonComponents;
        this.searchBySongService = searchBySongService;
    }

    @PostMapping("/search/by-song")
    public ModelAndView searchBySongToRedirect(HttpServletRequest request) {
        String query = request.getParameter("query");
        String seoQuery = SeoText.from(query);
        return new ModelAndView("redirect:/search/by-song/" + seoQuery);
    }

    @GetMapping("/search/by-song/{query}")
    public ModelAndView searchBySong(@PathVariable("query") String query,
                                     Pageable pageable) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("seoQuery", SeoText.from(query));
        items.put("query", SeoText.revert(query));
        items.put("foundSongs", searchBySongService.retrieve(query, pageable));
        items.put("submenu-search-by-song", true);
        return new ModelAndView("/search/by-song/index", items);
    }
}
