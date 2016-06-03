package pl.mszarlinski.udemy.microservices.discovery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private static final Optional<String> UNKNOWN_NAME = Optional.empty();

    private final ProducerClient producerClient;

    @Autowired
    public NameProvider(final ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @HystrixCommand(fallbackMethod = "getDefaultName",
        commandProperties = {
            @HystrixProperty(name = HYSTRIX_ERROR_THRESHOLD_PERCENTAGE, value = "20"),
            @HystrixProperty(name = HYSTRIX_SLEEP_WINDOW_IN_MILLISECONDS, value = "1000"),
        })
    Optional<String> tryFetchName() {
        return Optional.ofNullable(producerClient.getName());
    }

    private Optional<String> getDefaultName() {
        log.warn("Something went wrong. Returning default name.");
        return UNKNOWN_NAME;
    }
}
