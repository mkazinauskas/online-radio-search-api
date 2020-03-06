package com.modzo.ors.web.web.search.radio.station.by.played.song;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RadioStationsByPlayedSongController {

    private final CommonComponents commonComponents;

    private final RadioStationBySongService radioStationBySongService;

    private final SongService songService;

    public RadioStationsByPlayedSongController(CommonComponents commonComponents,
                                               RadioStationBySongService radioStationBySongService,
                                               SongService songService) {
        this.commonComponents = commonComponents;
        this.radioStationBySongService = radioStationBySongService;
        this.songService = songService;
    }

    @GetMapping("/radio-stations/by-played-song/{songId}")
    public ModelAndView searchBySong(@PathVariable("songId") long songId,
                                     Pageable pageable) {
        RadioStationBySongService.Data radioStations = radioStationBySongService.retrieveStationBy(songId, pageable);
        SongService.Data song = songService.retrieveSong(songId);

        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("song", song);
        items.put("radioStations", radioStations);
        return new ModelAndView("/search/by-song/radio-stations", items);
    }
}
