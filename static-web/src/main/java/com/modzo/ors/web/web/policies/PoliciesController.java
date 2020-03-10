package com.modzo.ors.web.web.policies;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PoliciesController {

    private final CommonComponents commonComponents;

    public PoliciesController(CommonComponents commonComponents) {
        this.commonComponents = commonComponents;
    }

    @GetMapping("/policies/privacy")
    public ModelAndView getPrivacyPolicy() {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Privacy Policy | Online Radio Stations, Internet Radio, " +
                "Free Music | OnlineRadioSearch.com");
        items.put(ComponentType.DESCRIPTION.getType(), "Privacy Policy. Listen to music from thousands of " +
                "internet radio stations streaming live. Search and browse all your favourite music genres.");
        items.put(ComponentType.KEYWORDS.getType(), "privacy, policy, shoutcast, web radio, mp3, aac, wmv, " +
                "streaming, dnas, shoutcast radio, stations, music, m3u, pls, winamp");

        return new ModelAndView("/policies/privacy", items);
    }
}
