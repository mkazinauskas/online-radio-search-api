package com.modzo.ors.domain.commands.add

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AddRadioStationHandler {

    @Transactional
    void handle(AddRadioStation command){

    }
}
