package com.example.paymentService.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentServiceErrorResponse {

	private String date;
	private String responseId;
	private String errorMessage;
	private String status;

}
