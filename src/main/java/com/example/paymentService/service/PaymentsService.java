package com.example.paymentService.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.paymentService.async.AsyncCommandInvoker;
import com.example.paymentService.async.AsyncCommandTask;
import com.example.paymentService.error.PaymentServiceError;
import com.example.paymentService.handler.CurrentPaymentHistoryHandler;
import com.example.paymentService.handler.PreviousPaymentHistoryHandler;
import com.example.paymentService.model.request.PaymentHistoryRequest;
import com.example.paymentService.model.response.PaymentHistoryResponse;
import com.example.paymentService.model.response.TransactionInfo;

@Service
public class PaymentsService implements IPaymentsService {

	private static String CURRENT = "current";
	private static String ALL = "all";

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

		} else if (request.getTimeRange().equalsIgnoreCase(ALL)) {
			// get all the data from System A and System B asynchronously

			AsyncCommandTask<Object> t1 = AsyncCommandTask.builder().commandName("current").asyncSupplier(
					() -> currentPaymentHistoryHandler.getCurrentPaymentHistory(request.getCustomerId(), "current"))
					.build();
			AsyncCommandTask<Object> t2 = AsyncCommandTask.builder().commandName("previous")
					.asyncSupplier(() -> previousPaymentHistoryHandler
							.getPreviousPaymentHistory(request.getCustomerId(), request.getTimeRange()))
					.build();

			Map<String, Object> objMap = AsyncCommandInvoker.getInstance().invokeAsync(Arrays.asList(t1, t2), 10);

			List<TransactionInfo> currentTransactions = (List<TransactionInfo>) objMap.get("current");
			List<TransactionInfo> previousTransactions = (List<TransactionInfo>) objMap.get("previous");

			List<TransactionInfo> allTransactions = currentTransactions;
			allTransactions.addAll(previousTransactions);

			Collections.sort(allTransactions);
			resp.setTransactions(allTransactions);

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
		List<String> allowedTimeRange = Arrays.asList("current", "all", "3 Months", "6 Months");

		if (null != request.getCustomerId()) {
			if (request.getCustomerId().length() != 5)
				throw new PaymentServiceError("CustomerId length should be of length 5");
		} else {
			throw new PaymentServiceError("CustomerId can not be null!");
		}

		if (null == request.getTimeRange()) {
			throw new PaymentServiceError("timeRange can not be null!");
		} else {
			if (!(request.getTimeRange().matches("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)*")
					|| allowedTimeRange.contains(request.getTimeRange()))) {
				throw new PaymentServiceError(
						"timeRange can only be any month followed by year(January2020/May2019..) or any of these values: current, all, 3 Months, 6 Months");
			}
		}

	}

}
