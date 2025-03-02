package com.flightIQ.Navigation.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightIQ.Navigation.Models.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
	Optional<Airport> findByIdent(String ident);
	Optional<Airport> findByIcao(String icao);
}
