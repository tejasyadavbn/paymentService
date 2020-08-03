package com.example.paymentService.model.request;

import lombok.Data;

@Data
public class PaymentHistoryRequest {

	private String customerId;
	private String timeRange;

}
