package com.modzo.ors.web.web.main;

import com.modzo.ors.web.web.api.RadioStationResponse;
import com.modzo.ors.web.web.api.RestPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "default", url = "${application.apiUrl}")
public interface RadioStationsClient {

    @GetMapping("/radio-stations")
    RestPageImpl<RadioStationResponse> getStations();
}
