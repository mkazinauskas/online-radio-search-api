package com.modzo.ors.web.search.radio.station;

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
public class SearchByRadioStationController {

    private final CommonComponents commonComponents;

    private final SearchByRadioStationService searchByRadioStationService;

    public SearchByRadioStationController(CommonComponents commonComponents,
                                          SearchByRadioStationService searchByRadioStationService) {
        this.commonComponents = commonComponents;
        this.searchByRadioStationService = searchByRadioStationService;
    }

    @PostMapping("/search/by-radio-station")
    public ModelAndView searchBySongToRedirect(HttpServletRequest request) {
        String query = request.getParameter("query");
        String seoQuery = SeoText.from(query);
        return new ModelAndView("redirect:/search/by-radio-station/" + seoQuery);
    }

    @GetMapping("/search/by-radio-station/{query}")
    public ModelAndView searchBySong(@PathVariable("query") String query, Pageable pageable) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), query + " results of free online radio stations, free mp3, " +
                "aac music at OnlineRadioSearch.com. Page " + (pageable.getPageNumber() + 1));

        items.put(ComponentType.DESCRIPTION.getType(), query + " online free radio search results. Browse "
                + query + " mp3 music radio station results. Page " + (pageable.getPageNumber() + 1)
        );
        items.put(ComponentType.KEYWORDS.getType(),
                query.replaceAll("-", ", ") + ", shoutcast, web radio, " +
                        "mp3, aac, wmv, streaming, dnas, shoutcast radio, music, m3u, pls"
        );

        items.put("seoQuery", SeoText.from(query));
        items.put("query", SeoText.revert(query));
        items.put("radioStations", searchByRadioStationService.retrieve(query, pageable));
        items.put("submenu-search-by-radio-station", true);
        return new ModelAndView("/search/by-radio-station/index", items);
    }
}
