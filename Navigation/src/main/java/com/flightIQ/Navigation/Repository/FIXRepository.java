package com.flightIQ.Navigation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightIQ.Navigation.Models.FIXX;

@Repository
public interface FIXRepository extends JpaRepository<FIXX, String> {
	FIXX findByFixId(String fixId);
}
