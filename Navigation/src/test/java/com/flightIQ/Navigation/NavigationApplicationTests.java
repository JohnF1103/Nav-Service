package com.flightIQ.Navigation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Integration tests to test REST endpoints
@EnabledIfSystemProperty(named = "it.cloudsql", matches = "true")
@TestPropertySource(locations = "/Navigation/src/main/resources/application-secret.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest (
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes={NavigationApplication.class},
		properties= {
		  "spring.datasource.username=${spring.datasource.username}",
		  "spring.datasource.password=${spring.datasource.password}",
		  "spring.cloud.gcp.sql.database-name=flightiq",
		  "spring.cloud.gcp.sql.instance-connection-name=${spring.cloud.gcp.sql.instance-connection-name}"
		})

		
class NavigationApplicationTests {

	@Autowired 
	private CommandLineRunner commandLineRunner;
	
	@Test
	void testGoogleCloudSQLConnection() {
		
	}

}
