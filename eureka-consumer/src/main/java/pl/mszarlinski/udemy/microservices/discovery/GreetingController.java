package pl.mszarlinski.udemy.microservices.discovery;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Value("${default-greeting}")
    private String defaultGreeting;

    /**
     * Separate bean is needed for a Hystrix aspect to work.
     */
    private final NameProvider nameProvider;

    @Autowired
    public GreetingController(final NameProvider nameProvider) {
        this.nameProvider = nameProvider;
    }

    @PostConstruct
    public void init() {
        log.info("***** defaultGreeting = " + defaultGreeting);
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return nameProvider.tryFetchName()
            .map(this::createGreeting)
            .orElse(defaultGreeting);
    }

    private String createGreeting(final String name) {
        return "Hi, " + name;
    }

}