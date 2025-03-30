package com.flightIQ.Navigation.Repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.flightIQ.Navigation.Models.FIXX;

@Repository
public interface FIXRepository extends Neo4jRepository<FIXX, String> {
	FIXX findByFixxId(String fixxId);
}
