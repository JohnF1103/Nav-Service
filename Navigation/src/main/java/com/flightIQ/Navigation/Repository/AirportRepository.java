package com.flightIQ.Navigation.Repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.flightIQ.Navigation.Models.Airport;

@Repository
public interface AirportRepository extends Neo4jRepository<Airport, String> {
	Optional<Airport> findByIdent(String ident);
	Optional<Airport> findByIcao(String icao);
}
