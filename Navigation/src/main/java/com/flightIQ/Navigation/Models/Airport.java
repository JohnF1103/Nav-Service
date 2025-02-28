package com.flightIQ.Navigation.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="FAA_Airports")
public class Airport {
	@Id
	@Column(name = "IDENT")
	private String ident;
	
	@Column(name = "ICAO_CODE")
	private String icao;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "LATITUDE")
	private double latitude;
	
	@Column(name="LONGITUDE")
	private double longitude;
	
	@Column(name="IAPEXISTS")
	private int iapExists;
	
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
