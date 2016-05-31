package pl.mszarlinski.udemy.microservices.discovery;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EurekaConsumerApplicationTests {

    private final static String LOCALHOST = "http://localhost";

    @LocalServerPort
    private String port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void shouldFetchNameAndReturnGreeting() {
        final String greeting = restTemplate.getForObject(LOCALHOST + ":" + port + "/greeting", String.class);
        assertThat(greeting).isNotEmpty();
    }

}
