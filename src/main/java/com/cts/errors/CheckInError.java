package com.cts.errors;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CheckInError {

	private final String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Default
	private List<String> errors = new ArrayList<>();
}
