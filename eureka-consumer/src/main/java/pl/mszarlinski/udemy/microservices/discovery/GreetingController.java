package pl.mszarlinski.udemy.microservices.discovery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private static final String DEFAULT_GREETING = "Default greeting";

    private final ProducerClient producerClient;

    @Autowired
    public GreetingController(final ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @RequestMapping("/greeting")
    public String greeting() {
        try {
            return Optional.ofNullable(producerClient.getName())
                .map(this::createGreeting)
                .orElse(DEFAULT_GREETING);
        } catch (Exception ex) {
            log.error("Error in greeting", ex);
            return DEFAULT_GREETING;
        }
    }

    private String createGreeting(final String name) {
        return "Hi, " + name;
    }

}