package com.example.paymentService.model.response;

import lombok.Data;

@Data
public class TransactionInfo implements Comparable<TransactionInfo> {

	private String title;
	private String amount;
	private String date;
	private String method;
	private String cardUsed;
	private String category;
	private String referenceNumber;
	private String statementPeriod;

	@Override
	public int compareTo(TransactionInfo o) {
		return o.getDate().compareTo(this.getDate());
	}

}
