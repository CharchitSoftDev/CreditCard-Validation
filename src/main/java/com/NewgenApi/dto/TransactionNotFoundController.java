package com.NewgenApi.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionNotFoundController {
	@ExceptionHandler(value = TransactionNotFoundException.class)
	public ResponseEntity<Object> exception(TransactionNotFoundException tex) {
		return new ResponseEntity<>(new ErrorResponse("Transaction Not Found", 124), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
		error.setErrorCode(144);
		return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
	}
}
