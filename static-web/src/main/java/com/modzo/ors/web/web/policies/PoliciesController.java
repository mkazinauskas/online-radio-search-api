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
        items.put(ComponentType.PAGE_TITLE.getType(), "Privacy Policy | Online Radio Search");
        return new ModelAndView("/policies/privacy", items);
    }

    @GetMapping("/policies/eula")
    public ModelAndView getEula() {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "End User License Agreement and Terms of Service | Online Radio Search");
        return new ModelAndView("/policies/eula", items);
    }

    @GetMapping("/policies/cookie")
    public ModelAndView getCookiePolicy() {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Cookie Policy | Online Radio Search");
        return new ModelAndView("/policies/cookie", items);
    }

    @GetMapping("/policies/privacy-shield")
    public ModelAndView getPrivacyShield() {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Privacy Shield | Online Radio Search");
        return new ModelAndView("/policies/privacy-shield", items);
    }
}
