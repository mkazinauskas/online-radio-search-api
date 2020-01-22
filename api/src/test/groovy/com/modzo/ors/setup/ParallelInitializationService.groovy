package com.modzo.ors.setup

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Component
@Slf4j
class ParallelInitializationService {

    ParallelInitializationService(List<ParallelInitializationBean> servicesToInit) {
        ExecutorService threadPool = Executors.newFixedThreadPool(servicesToInit.size())
        try {
            List<Future> futures = servicesToInit.collect { service ->
                threadPool.submit({ service.initialize() } as Callable)
            }
            futures.each { it.get() }
        } catch (Exception exception) {
            log.error('Failed to start container', exception)
            throw new IllegalStateException('Failed to load services in parallel', exception)
        } finally {
            threadPool.shutdown()
        }
    }
}
