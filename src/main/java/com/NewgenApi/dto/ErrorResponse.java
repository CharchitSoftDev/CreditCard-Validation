package com.NewgenApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	private String message;
	private int errorCode;

	public ErrorResponse(String message, int errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}

}
