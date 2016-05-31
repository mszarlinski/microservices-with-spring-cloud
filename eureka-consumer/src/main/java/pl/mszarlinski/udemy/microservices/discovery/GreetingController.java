package pl.mszarlinski.udemy.microservices.discovery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private static final String PRODUCER_SERVICE_ID = "PRODUCER";

    private static final String DEFAULT_GREETING = "Default greeting";

    private final RestTemplate restTemplate;

    @Autowired
    public GreetingController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/greeting")
    public String greeting() {
        try {
            return Optional.ofNullable(restTemplate.getForObject("http://" + PRODUCER_SERVICE_ID + "/name", String.class))
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