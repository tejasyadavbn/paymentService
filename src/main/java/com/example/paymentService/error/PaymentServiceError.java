package com.example.paymentService.error;

public class PaymentServiceError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaymentServiceError(String message) {
		super(message);
	}

}
