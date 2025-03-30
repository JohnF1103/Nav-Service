package com.flightIQ.Navigation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.flightIQ.Navigation.Repository.AirportRepository;

@DataJpaTest
@TestPropertySource(locations = "/Navigation/src/main/resources/application-secret.properties")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class CloudSQLConnectionTest {
	
	private final AirportRepository airportRepository = null;

	@Test
	void testAirportDBConnection() {
		
	}

	@Test
	void testFIXXDBConnection() {
		
	}
}
