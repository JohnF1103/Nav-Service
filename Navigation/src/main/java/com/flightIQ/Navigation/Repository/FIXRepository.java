package com.flightIQ.Navigation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightIQ.Navigation.Models.FIXX;

public interface FIXRepository extends JpaRepository<FIXX, String> {
	FIXX findFIXXByCode(String fixId);
}
