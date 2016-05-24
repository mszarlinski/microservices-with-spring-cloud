package pl.mszarlinski.udemy.microservices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mszarlinski on 2016-05-24.
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/hello")
    @ResponseBody public String hello() {
        return "Hello world!";
    }
}
