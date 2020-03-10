package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import com.modzo.ors.web.web.components.common.model.RadioStationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RadioStationController {

    private final CommonComponents commonComponents;

    private final RadioStationService radioStationService;

    private final RadioStationStreamService stationStreamService;

    private final RadioStationSongsService radioStationSongsService;

    public RadioStationController(CommonComponents commonComponents,
                                  RadioStationService radioStationService,
                                  RadioStationStreamService stationStreamService,
                                  RadioStationSongsService radioStationSongsService) {
        this.commonComponents = commonComponents;
        this.radioStationService = radioStationService;
        this.stationStreamService = stationStreamService;
        this.radioStationSongsService = radioStationSongsService;
    }

    @GetMapping("/radio-stations/{radioStationSeoTitle}/{id}")
    public ModelAndView radioStation(
            @PathVariable("radioStationSeoTitle") String radioStationSeoTitle,
            @PathVariable("id") Long id,
            Pageable pageable) {
        RadioStationModel radioStation = radioStationService.retrieve(id);

        if (!StringUtils.pathEquals(radioStationSeoTitle, radioStation.getSeoTitle())) {
            throw new IllegalArgumentException("Radio station not found");
        }

        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), radioStation.getTitle() + " online radio station and played " +
                "songs, free music | OnlineRadioSearch.com"
        );
        items.put(ComponentType.DESCRIPTION.getType(), radioStation.getTitle() + " listen to free online radio " +
                "station. Play popular mp3 music for free."
        );
        items.put(ComponentType.KEYWORDS.getType(),
                radioStation.getSeoTitle().replaceAll("-", ", ") + ", shoutcast, web radio, " +
                        "mp3, aac, wmv, streaming, dnas, shoutcast radio, music, m3u, pls"
        );

        items.put("radioStation", radioStation);
        items.put("radioStationStreams", stationStreamService.retrieve(id));
        items.put("radioStationSongs", radioStationSongsService.retrieve(id, pageable));
        return new ModelAndView("/radio-station/index", items);
    }
}
