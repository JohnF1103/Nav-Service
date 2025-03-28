package com.flightIQ.Navigation.Models;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("FIXX")
public class FIXX {
	
	@Id
	@Property("fixId")
	private String fixId;
	
	@Property("latitude")
	private double latitude;
	
	@Property("longitude")
	private double longitude;
	
	@Relationship(type="CONNECTS_WITH", direction = Direction.INCOMING)
	private List<FIXX> nearbyFIXXES = new ArrayList<>();
	
	@Override
	public String toString() {
	    return "FIXX{" +
	            "fix_id='" + fixId + '\'' +
	            ", latitude=" + latitude +
	            ", longitude=" + longitude +
	            '}';
	}

}
