package com.flightIQ.Navigation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class NavigationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavigationApplication.class, args);
	}

}
