package com.example.paymentService.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.paymentService.dao.IPaymentsHistoryDAO;
import com.example.paymentService.error.PaymentServiceError;
import com.example.paymentService.model.response.TransactionInfo;

@Component
public class PaymentsServiceHelper {

	@Autowired
	IPaymentsHistoryDAO paymentsHistoryDAO;

	// gets data from CSV: systemA.csv
	public List<TransactionInfo> getTransactionInfoFromCSV(String customerId, String timeRange) {

		List<TransactionInfo> transactionList = new ArrayList<TransactionInfo>();

		String fileName = timeRange.equalsIgnoreCase("current") ? "/systemA.csv" : "/systemB.csv";

		Pattern pattern = Pattern.compile(",");
		InputStream is = getClass().getResourceAsStream(fileName);
		try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));) {

			transactionList = bufferReader.lines().skip(1).map(line -> {
				String[] values = pattern.split(line);

				TransactionInfo tInfo = new TransactionInfo();
				tInfo.setTitle(values[0]);
				tInfo.setAmount(values[1]);
				tInfo.setDate(values[2]);
				tInfo.setMethod(values[3]);
				tInfo.setCardUsed(values[4]);
				tInfo.setCategory(values[5]);
				tInfo.setReferenceNumber(values[6]);
				tInfo.setStatementPeriod(values[7]);

				return tInfo;
			}).collect(Collectors.toList());

		} catch (FileNotFoundException e) {
			throw new PaymentServiceError("Error getting data from System");
		} catch (IOException e) {
			throw new PaymentServiceError("Error getting data from System");
		}

		return transactionList;
	}

	// gets data from currentStatement DB
	public List<TransactionInfo> getCurrentTransactionInfoFromDB(String customerId, String timeRange) {

		List<TransactionInfo> currentTransactions = paymentsHistoryDAO.getCurrentPaymentHistory(customerId, timeRange)
				.stream().map(t -> {

					TransactionInfo tInfo = new TransactionInfo();
					tInfo.setTitle(t.getTitle());
					tInfo.setAmount(t.getAmount());
					tInfo.setDate(t.getDate());
					tInfo.setMethod(t.getMethod());
					tInfo.setCardUsed(t.getCardUsed());
					tInfo.setCategory(t.getCategory());
					tInfo.setReferenceNumber(t.getReferenceNumber());
					tInfo.setStatementPeriod(t.getStatementPeriod());

					return tInfo;
				}).collect(Collectors.toList());

		return currentTransactions;
	}

	// gets data from previousStatement DB
	public List<TransactionInfo> getPreviousTransactionInfoFromDB(String customerId, String timeRange) {

		// if the timeRange starts with number ie., multiple months, get all the records
		// from DB if not, get data for that particular month.statement period
		if ("ALLRECORDS".equalsIgnoreCase(timeRange)) {

			List<TransactionInfo> currentTransactions = paymentsHistoryDAO.getAllPreviousPaymentHistory(customerId)
					.stream().map(t -> {

						TransactionInfo tInfo = new TransactionInfo();
						tInfo.setTitle(t.getTitle());
						tInfo.setAmount(t.getAmount());
						tInfo.setDate(t.getDate());
						tInfo.setMethod(t.getMethod());
						tInfo.setCardUsed(t.getCardUsed());
						tInfo.setCategory(t.getCategory());
						tInfo.setReferenceNumber(t.getReferenceNumber());
						tInfo.setStatementPeriod(t.getStatementPeriod());

						return tInfo;
					}).collect(Collectors.toList());

			return currentTransactions;

		} else {
			List<TransactionInfo> currentTransactions = paymentsHistoryDAO
					.getPreviousPaymentHistory(customerId, timeRange).stream().map(t -> {

						TransactionInfo tInfo = new TransactionInfo();
						tInfo.setTitle(t.getTitle());
						tInfo.setAmount(t.getAmount());
						tInfo.setDate(t.getDate());
						tInfo.setMethod(t.getMethod());
						tInfo.setCardUsed(t.getCardUsed());
						tInfo.setCategory(t.getCategory());
						tInfo.setReferenceNumber(t.getReferenceNumber());
						tInfo.setStatementPeriod(t.getStatementPeriod());

						return tInfo;
					}).collect(Collectors.toList());

			return currentTransactions;
		}
	}

}
