package com.modzo.ors.web.main;

import com.modzo.ors.web.components.CommonComponents;
import com.modzo.ors.web.components.ComponentType;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainPageController {

    private final CommonComponents commonComponents;

    private final LatestRadioStations latestRadioStations;

    public MainPageController(CommonComponents commonComponents,
                              LatestRadioStations latestRadioStations) {
        this.commonComponents = commonComponents;
        this.latestRadioStations = latestRadioStations;
    }

    @GetMapping("/")
    public ModelAndView getMainPage(Pageable pageable) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Stations, Internet Radio, Free Music " +
                "| OnlineRadioSearch.com");
        items.put(ComponentType.DESCRIPTION.getType(), "Listen to music from thousands of internet radio stations " +
                "streaming live. Search and browse all your favourite music genres.");
        items.put(ComponentType.KEYWORDS.getType(), "shoutcast, web radio, mp3, aac, wmv, streaming, dnas, " +
                "shoutcast radio, stations, music, m3u, pls, winamp");

        items.put("latestRadioStations", latestRadioStations.retrieve(pageable));
        return new ModelAndView("/main/index", items);
    }
}
