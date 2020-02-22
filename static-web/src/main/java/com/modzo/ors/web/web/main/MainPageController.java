package com.modzo.ors.web.web.main;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainPageController {

    private final CommonComponents commonComponents;

    public MainPageController(CommonComponents commonComponents) {
        this.commonComponents = commonComponents;
    }

    @GetMapping("/")
    public ModelAndView getMainPage() {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        return new ModelAndView("/main/index", items);
    }
}
