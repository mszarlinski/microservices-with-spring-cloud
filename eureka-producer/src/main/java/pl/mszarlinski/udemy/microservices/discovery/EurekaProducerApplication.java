package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class EurekaProducerApplication {

    private final static String[] NAMES = {"Ala", "Barbara", "Zosia"};

    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerApplication.class, args);
    }

    @RequestMapping("/name")
    public String produce() {
        return NAMES[(int) (Math.random() * NAMES.length)];
    }
}
