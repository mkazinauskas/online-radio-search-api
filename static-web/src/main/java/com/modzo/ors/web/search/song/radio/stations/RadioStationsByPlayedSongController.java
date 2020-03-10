package com.modzo.ors.web.search.song.radio.stations;

import com.modzo.ors.web.components.CommonComponents;
import com.modzo.ors.web.components.ComponentType;
import com.modzo.ors.web.components.common.Paged;
import com.modzo.ors.web.components.common.model.RadioStationModel;
import com.modzo.ors.web.utils.SeoText;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.pathEquals;

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

    @GetMapping("/radio-stations/by-played-song/{songTitle}/{songId}")
    public ModelAndView searchBySong(
            @PathVariable("songTitle") String songTitle,
            @PathVariable("songId") long songId,
            Pageable pageable) {
        var song = songService.retrieveSong(songId);

        if (!pathEquals(song.getSeoTitle(), songTitle)) {
            throw new IllegalArgumentException("Song not found...");
        }

        Paged<RadioStationModel> radioStations = radioStationBySongService.retrieveStationBy(songId, pageable);

        Map<String, Object> items = new HashMap<>(commonComponents.load());

        String nonSeoTitle = SeoText.revert(songTitle);
        items.put(ComponentType.PAGE_TITLE.getType(), nonSeoTitle
                + " results of free online radio stations, free mp3, "
                + "aac music at OnlineRadioSearch.com. Page " + (pageable.getPageNumber() + 1));

        items.put(ComponentType.DESCRIPTION.getType(), nonSeoTitle + " online free radio search results. Browse "
                + nonSeoTitle + " mp3 music radio station results. Page " + (pageable.getPageNumber() + 1)
        );
        items.put(ComponentType.KEYWORDS.getType(),
                songTitle.replaceAll("-", ", ") + ", shoutcast, web radio, "
                        + "mp3, aac, wmv, streaming, dnas, shoutcast radio, music, m3u, pls"
        );

        items.put("song", song);
        items.put("radioStations", radioStations);
        return new ModelAndView("/search/by-song/radio-stations", items);
    }
}
