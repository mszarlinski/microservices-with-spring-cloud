package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class EurekaProducerApplication {

    @Value("${names}")
    private String names;

    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerApplication.class, args);
    }

    @RequestMapping("/name")
    public String produce() {
        final String[] namesArray = names.split(",");
        return namesArray[(int) (Math.random() * namesArray.length)];
    }
}
