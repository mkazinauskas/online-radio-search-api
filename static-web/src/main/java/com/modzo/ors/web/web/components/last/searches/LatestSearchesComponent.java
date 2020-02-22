package com.modzo.ors.web.web.components.last.searches;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LatestSearchesComponent {

    public List<LatestSearchesData> retrieve() {
        return List.of(new LatestSearchesData("testt"));
    }
}
