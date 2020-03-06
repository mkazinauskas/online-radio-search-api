package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import com.modzo.ors.web.web.components.common.model.RadioStationModel;
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

    public RadioStationController(CommonComponents commonComponents,
                                  RadioStationService radioStationService,
                                  RadioStationStreamService stationStreamService) {
        this.commonComponents = commonComponents;
        this.radioStationService = radioStationService;
        this.stationStreamService = stationStreamService;
    }

    @GetMapping("/radio-stations/{radioStationSeoTitle}/{id}")
    public ModelAndView radioStation(
            @PathVariable("radioStationSeoTitle") String radioStationSeoTitle,
            @PathVariable("id") Long id) {
        RadioStationModel radioStation = radioStationService.retrieve(id);

        if (!StringUtils.pathEquals(radioStationSeoTitle, radioStation.getSeoTitle())) {
            throw new IllegalArgumentException("Radio station not found");
        }

        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("radioStation", radioStation);
        items.put("radioStationStreams", stationStreamService.retrieve(id));
        return new ModelAndView("/radio-station/index", items);
    }
}
