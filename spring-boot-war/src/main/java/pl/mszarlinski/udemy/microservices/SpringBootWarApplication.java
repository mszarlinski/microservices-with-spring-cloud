package pl.mszarlinski.udemy.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWarApplication {

	/**
	 * Used whe run as a JAR
     */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWarApplication.class, args);
	}
}
