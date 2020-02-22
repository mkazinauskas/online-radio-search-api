package com.modzo.ors.web.web.search;

import com.modzo.ors.web.web.components.CommonComponents;
import com.modzo.ors.web.web.components.ComponentType;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchBySongController {

    private final CommonComponents commonComponents;

    public SearchBySongController(CommonComponents commonComponents) {
        this.commonComponents = commonComponents;
    }

    @PostMapping("/search/by-song")
    public ModelAndView searchBySongToRedirect(HttpServletRequest request) {
        String query = request.getParameter("query");
        String seoQuery = SeoText.from(query);
        return new ModelAndView("redirect:/search/by-song/" + seoQuery);
    }

    @GetMapping("/search/by-song/{query}")
    public ModelAndView searchBySong(@PathVariable("query") String query) {
        Map<String, Object> items = new HashMap<>(commonComponents.load());
        items.put(ComponentType.PAGE_TITLE.getType(), "Online Radio Search. Millions of free online radio stations");
        items.put("query", query);
        return new ModelAndView("/search/by-song/index", items);
    }
}
