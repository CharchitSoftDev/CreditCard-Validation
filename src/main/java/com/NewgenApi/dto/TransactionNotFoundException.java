package com.NewgenApi.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

	public final static long serialVersionUID = 1L;

	public TransactionNotFoundException() {
		// TODO Auto-generated constructor stub
	}
}
