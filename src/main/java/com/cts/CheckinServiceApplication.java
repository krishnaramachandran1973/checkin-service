package com.cts;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.cts.domain.CheckInRecord;
import com.cts.repository.CheckInRepository;
import com.cts.service.CheckInService;

@EnableDiscoveryClient
@SpringBootApplication
public class CheckinServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(CheckinServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CheckinServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CheckInService service, CheckInRepository repository) {
		return args -> {

			CheckInRecord record = CheckInRecord.builder()
					.firstName("Krishnakumar")
					.lastName("Ramachandran")
					.seatNumber("28A")
					.checkInTime(LocalDateTime.now())
					.flightNumber("BF101")
					.flightDate(LocalDate.of(2021, 9, 18))
					.bookingId(1L)
					.build();

			Long id = service.checkIn(record);
			logger.info("Checked in successfully ...");

			logger.info("Looking to load checkedIn record...");
			logger.info("Result: " + repository.findById(id)
					.get());

		};
	}
}
