package com.example.paymentService.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PaymentServiceErrorControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<Object> handleExceptions(Exception ex) {
		PaymentServiceErrorResponse errorDetails = new PaymentServiceErrorResponse(LocalDateTime.now().toString(),
				Long.toString(Math.round(Math.random() * 100000000)), ex.getMessage(), "ERROR");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
