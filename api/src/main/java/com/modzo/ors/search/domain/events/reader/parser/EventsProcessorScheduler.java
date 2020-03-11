package com.modzo.ors.search.domain.events.reader.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.events.processor.enabled", havingValue = "true")
class EventsProcessorScheduler {

    private final Logger log = LoggerFactory.getLogger(EventsProcessorScheduler.class);

    private final EventsProcessor processor;

    public EventsProcessorScheduler(EventsProcessor processor) {
        this.processor = processor;
    }

    @Scheduled(fixedDelay = 1000L)
    void process() {
        log.info("Executing events processor.");
        processor.process();
        log.info("Finished executing processor.");
    }
}
