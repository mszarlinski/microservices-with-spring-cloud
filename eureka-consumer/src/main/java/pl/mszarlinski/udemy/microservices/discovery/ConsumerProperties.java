package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mszarlinski on 2016-06-10.
 */
@Component
@ConfigurationProperties
public class ConsumerProperties {

    private String defaultGreeting; // relaxed property name binding

    public String getDefaultGreeting() {
        return defaultGreeting;
    }

    public void setDefaultGreeting(final String defaultGreeting) {
        this.defaultGreeting = defaultGreeting;
    }
}
