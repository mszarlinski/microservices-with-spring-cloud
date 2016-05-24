package pl.mszarlinski.udemy.microservices.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class ConfigClientApplication {

    static {
//        System.setProperty("spring.profiles.active", "???");
//        System.setProperty("spring.profiles.active", "dev");
        System.setProperty("spring.profiles.active", "prod");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigClientApplication.class, args);
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();

        System.out.println(environment.getProperty("info-message"));
        System.out.println(environment.getProperty("debug-mode"));
    }
}
