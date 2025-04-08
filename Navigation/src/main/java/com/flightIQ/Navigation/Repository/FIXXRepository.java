package com.flightIQ.Navigation.Repository;


import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.flightIQ.Navigation.Models.FIXX;

@Repository
public interface FIXXRepository extends Neo4jRepository<FIXX, String> {
	Optional<FIXX> findByFixxId(String fixxId);
}
