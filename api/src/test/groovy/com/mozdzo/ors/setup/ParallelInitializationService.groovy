package com.mozdzo.ors.setup

import org.springframework.stereotype.Component

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Component
class ParallelInitializationService {

    ParallelInitializationService(List<ParallelInitializationBean> servicesToInit) {
        ExecutorService threadPool = Executors.newFixedThreadPool(servicesToInit.size())
        try {
            List<Future> futures = servicesToInit.collect { service ->
                threadPool.submit({ service.initialize() } as Callable)
            }
            futures.each { it.get() }
        } catch (Exception exception) {
            throw new RuntimeException(exception)
        } finally {
            threadPool.shutdown()
        }
    }
}
