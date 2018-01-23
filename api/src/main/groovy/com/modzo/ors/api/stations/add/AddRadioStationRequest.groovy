package com.modzo.ors.api.stations.add

import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.URL

class AddRadioStationRequest {
    @URL
    @NotBlank
    String url

    @NotBlank
    String name
}
