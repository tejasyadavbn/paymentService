package com.example.paymentService.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.paymentService.helper.PaymentsServiceHelper;
import com.example.paymentService.model.response.TransactionInfo;

@Component
public class CurrentPaymentHistoryHandler {

	@Autowired
	PaymentsServiceHelper paymentsServiceHelper;

	@Value("${get.data.from.db}")
	public String getDataFromDB;

	public List<TransactionInfo> getCurrentPaymentHistory(String customerId, String timeRange) {

		// Backend call goes here.
		// For demonstration, using data from CSV file. systemA.csv OR DB
		if ("Y".equalsIgnoreCase(getDataFromDB)) {
			return paymentsServiceHelper.getCurrentTransactionInfoFromDB(customerId, timeRange);
		} else {
			return paymentsServiceHelper.getTransactionInfoFromCSV(customerId, timeRange);
		}
	}

}
