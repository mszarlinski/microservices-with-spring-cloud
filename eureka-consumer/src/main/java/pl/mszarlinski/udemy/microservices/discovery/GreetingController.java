package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    private static final String DEFAULT_GREETING = "Default greeting";

    /**
     * Separate bean is needed for a Hystrix aspect to work.
     */
    private final NameProvider nameProvider;

    @Autowired
    public GreetingController(final NameProvider nameProvider) {
        this.nameProvider = nameProvider;
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return nameProvider.tryFetchName()
            .map(this::createGreeting)
            .orElse(DEFAULT_GREETING);
    }

    private String createGreeting(final String name) {
        return "Hi, " + name;
    }

}