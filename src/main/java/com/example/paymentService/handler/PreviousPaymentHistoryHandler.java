package com.example.paymentService.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.paymentService.helper.PaymentsServiceHelper;
import com.example.paymentService.model.response.TransactionInfo;

@Component
public class PreviousPaymentHistoryHandler {

	@Autowired
	PaymentsServiceHelper paymentsServiceHelper;

	@Value("${get.data.from.db}")
	public String getDataFromDB;

	public List<TransactionInfo> getPreviousPaymentHistory(String customerId, String timeRange) {

		// Backend call goes here. For demonstration, using data from CSV file.
		// systemB.csv /DB
		if ("Y".equalsIgnoreCase(getDataFromDB)) {
			if (Character.isDigit(timeRange.charAt(0))) {
				return paymentsServiceHelper.getPreviousTransactionInfoFromDB(customerId, "ALLRECORDS");
			} else {
				return paymentsServiceHelper.getPreviousTransactionInfoFromDB(customerId, timeRange);
			}
		} else {
			if (Character.isDigit(timeRange.charAt(0))) {
				return paymentsServiceHelper.getTransactionInfoFromCSV(customerId, timeRange);
			} else {
				return paymentsServiceHelper.getTransactionInfoFromCSV(customerId, timeRange).stream()
						.filter(tInfo -> tInfo.getStatementPeriod().equalsIgnoreCase(timeRange))
						.collect(Collectors.toList());
			}
		}

	}

}
