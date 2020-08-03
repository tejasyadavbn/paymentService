package com.example.paymentService.service;

import org.springframework.stereotype.Component;

import com.example.paymentService.model.request.PaymentHistoryRequest;
import com.example.paymentService.model.response.PaymentHistoryResponse;

@Component
public interface IPaymentsService {
	
	public PaymentHistoryResponse getPaymentHistory(PaymentHistoryRequest request);

}
