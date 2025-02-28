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
@Table(name="FAA_FIXX")
public class FIXX {
	
	@Id
	@Column(name="fix")
	private String fix_id;
	
	@Column(name="lat")
	private double latitude;
	
	@Column(name="long")
	private double longitude;
	
	@Override
	public String toString() {
	    return "FIXX{" +
	            "fix_id='" + fix_id + '\'' +
	            ", latitude=" + latitude +
	            ", longitude=" + longitude +
	            '}';
	}

}
