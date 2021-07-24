package com.cts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.domain.CheckInRecord;
import com.cts.repository.CheckInRepository;
import com.cts.service.CheckInService;
import com.cts.vo.BookingUpdate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CheckInServiceImpl implements CheckInService {
	private static final Logger logger = LoggerFactory.getLogger(CheckInServiceImpl.class);

	private final CheckInRepository checkinRepository;
	private final RabbitTemplate rabbitTemplate;

	@Value("${checkin.amqp-check-in-queue}")
	String checkInQ;

	@Override
	public Long checkIn(CheckInRecord checkIn) {
		checkIn.setCheckInTime(LocalDateTime.now());
		logger.info(">> Checking in");
		CheckInRecord cr = checkinRepository.save(checkIn);
		logger.info("Successfully saved checkin ");

		logger.info("Sending booking id " + cr.getBookingId());
		BookingUpdate bookingUpdate = BookingUpdate.builder()
				.bookingId(cr.getBookingId())
				.build();
		rabbitTemplate.convertAndSend(checkInQ, bookingUpdate);

		return cr.getId();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<CheckInRecord> getCheckInRecord(Long id) {
		return checkinRepository.findById(id);
	}

}
