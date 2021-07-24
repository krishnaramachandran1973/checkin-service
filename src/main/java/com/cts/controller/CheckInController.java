package com.cts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.domain.CheckInRecord;
import com.cts.errors.CheckInError;
import com.cts.service.CheckInService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/checkin")
public class CheckInController {
	private static final Logger logger = LoggerFactory.getLogger(CheckInController.class);

	private final CheckInService checkInService;

	@PostMapping
	public ResponseEntity<?> checkIn(@RequestBody @Valid CheckInRecord checkIn, Errors errors) {
		if (errors.hasErrors()) {
			logger.info("Bad request for BookingRecord");
			List<String> ers = new ArrayList<>();
			errors.getFieldErrors()
					.forEach(er -> ers.add(er.getField() + ": " + er.getDefaultMessage()));
			return ResponseEntity.badRequest()
					.body(CheckInError.builder()
							.message("Invalid CheckInRecord")
							.errors(ers)
							.build());
		}
		return ResponseEntity.ok(checkInService.checkIn(checkIn));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCheckIn(@PathVariable Long id) {
		Optional<CheckInRecord> cir = checkInService.getCheckInRecord(id);
		if (cir.isPresent()) {
			return ResponseEntity.ok(cir.get());
		}
		return ResponseEntity.badRequest()
				.body(CheckInError.builder()
						.message("No Record found")
						.build());
	}

}
