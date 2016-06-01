package pl.mszarlinski.udemy.microservices.discovery;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mszarlinski on 2016-06-01.
 */
@FeignClient("PRODUCER")
public interface ProducerClient {

    @RequestMapping("/name")
    String getName();
}
