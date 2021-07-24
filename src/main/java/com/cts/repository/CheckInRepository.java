package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.domain.CheckInRecord;

public interface CheckInRepository extends JpaRepository<CheckInRecord, Long>{

}
