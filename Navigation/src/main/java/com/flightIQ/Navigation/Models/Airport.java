package com.flightIQ.Navigation.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Airport")
public class Airport {

	@Id
	@Property("ident")
	private String ident;
	
	@Property("icao")
	private String icao;
	
	@Property("name")
	private String name;
	
	@Property("latitude")
	private String latitude;
	
	@Property("longitude")
	private String longitude;
	
	@Property("iapExists")
	private String iapExists;
	
//	@Relationship(type="HAS_DEPARTURE_FIX", direction=Direction.OUTGOING)
//	private List<FIXX> departureFixxes = new ArrayList<>();
//	
//	@Relationship(type="HAS_ARRIVAL_FIX", direction=Direction.OUTGOING)
//	private List<FIXX> arrivalFixxes = new ArrayList<>();
	
	
	@Override
	public String toString() {
	    return "Airport{" +
	            "ident='" + ident + '\'' +
	            ", icao='" + icao + '\'' +
	            ", name='" + name + '\'' +
	            ", latitude=" + latitude +
	            ", longitude=" + longitude +
	            ", iapExists=" + iapExists +
	            '}';
	}
}
