package com.cts.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ConfigurationProperties(prefix = "checkin")
public class CheckInProperties {
	private String amqpCheckInQueue;

}
