package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RadioStationController {

    private final CommonComponents commonComponents;

    private final RadioStationService radioStationService;

    public RadioStationController(CommonComponents commonComponents, RadioStationService radioStationService) {
        this.commonComponents = commonComponents;
        this.radioStationService = radioStationService;
    }

    @GetMapping("/radio-stations/{id}")
    public ModelAndView radioStation(@PathVariable("id") Long id) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("radioStation", radioStationService.retrieve(id));
        return new ModelAndView("/radio-station/index", items);
    }
}
