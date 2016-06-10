package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    /**
     * Separate bean is needed for a Hystrix aspect to work.
     */
    private final NameProvider nameProvider;

    private final ConsumerProperties consumerProperties;

    @Autowired
    public GreetingController(final NameProvider nameProvider, final ConsumerProperties consumerProperties) {
        this.nameProvider = nameProvider;
        this.consumerProperties = consumerProperties;
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return nameProvider.tryFetchName()
            .map(this::createGreeting)
            .orElse(consumerProperties.getDefaultGreeting());
    }

    private String createGreeting(final String name) {
        return "Hi, " + name;
    }

}