package com.modzo.ors.configuration.exception.handler;

import com.modzo.ors.stations.domain.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler({DomainException.class})
    public final ResponseEntity<DomainApiError> handleDomainException(DomainException ex) {
        DomainApiError domainApiError = new DomainApiError(ex.getId(), ex.getMessage());
        return ResponseEntity.badRequest().body(domainApiError);
    }
}