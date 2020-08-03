package com.example.paymentService.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.paymentService.error.PaymentServiceError;
import com.example.paymentService.handler.CurrentPaymentHistoryHandler;
import com.example.paymentService.handler.PreviousPaymentHistoryHandler;
import com.example.paymentService.model.request.PaymentHistoryRequest;
import com.example.paymentService.model.response.PaymentHistoryResponse;
import com.example.paymentService.model.response.TransactionInfo;

@Service
public class PaymentsService implements IPaymentsService {

	private static String CURRENT = "current";

	@Autowired
	CurrentPaymentHistoryHandler currentPaymentHistoryHandler;

	@Autowired
	PreviousPaymentHistoryHandler previousPaymentHistoryHandler;

	@Override
	public PaymentHistoryResponse getPaymentHistory(PaymentHistoryRequest request) {

		validateRequest(request);

		PaymentHistoryResponse resp = new PaymentHistoryResponse();

		if (request.getTimeRange().equalsIgnoreCase(CURRENT)) {

			List<TransactionInfo> currentTransactions = currentPaymentHistoryHandler
					.getCurrentPaymentHistory(request.getCustomerId(), request.getTimeRange());

			// sorts according to the date
			Collections.sort(currentTransactions);
			resp.setTransactions(currentTransactions);

		} else {

			List<TransactionInfo> previousTransactions = previousPaymentHistoryHandler
					.getPreviousPaymentHistory(request.getCustomerId(), request.getTimeRange());

			// sorts according to the date
			Collections.sort(previousTransactions);
			resp.setTransactions(previousTransactions);

		}

		return resp;
	}

	// request validations
	private void validateRequest(PaymentHistoryRequest request) {
		if (null != request.getCustomerId()) {
			if (request.getCustomerId().length() != 5)
				throw new PaymentServiceError("CustomerId length should be of length 5");
		} else {
			throw new PaymentServiceError("CustomerId can not be null!");
		}

		if (null == request.getTimeRange()) {
			throw new PaymentServiceError("timeRange can not be null!");
		}

	}

}
