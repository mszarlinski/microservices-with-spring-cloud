package pl.mszarlinski.udemy.microservices.discovery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author mszarlinski on 2016-06-03.
 */
@Component
class NameProvider {
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private static final String HYSTRIX_ERROR_THRESHOLD_PERCENTAGE = "circuitBreaker.errorThresholdPercentage";

    private static final String HYSTRIX_SLEEP_WINDOW_IN_MILLISECONDS = "circuitBreaker.sleepWindowInMilliseconds";

    private static final String UNKNOWN_NAME = null;

    private final RestTemplate restTemplate;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    public NameProvider(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getDefaultName",
        commandProperties = {
            @HystrixProperty(name = HYSTRIX_ERROR_THRESHOLD_PERCENTAGE, value = "20"),
            @HystrixProperty(name = HYSTRIX_SLEEP_WINDOW_IN_MILLISECONDS, value = "1000"),
        })
    Future<String> tryFetchName() {
        return executorService.submit(() -> restTemplate.getForObject("http://PRODUCER/name", String.class));
    }

    private String getDefaultName() {
        log.warn("Something went wrong. Returning default name.");
        return UNKNOWN_NAME;
    }
}
