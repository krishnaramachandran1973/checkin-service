package com.cts.service;

import java.util.Optional;

import com.cts.domain.CheckInRecord;

public interface CheckInService {
	Long checkIn(CheckInRecord checkIn);

	Optional<CheckInRecord> getCheckInRecord(Long id);

}
