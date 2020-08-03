package com.example.paymentService.model.response;

import java.util.List;

import lombok.Data;

@Data
public class PaymentHistoryResponse {

	// card num -> List of transactions
	// private Map<String, List<TransactionInfo>> transactions;

	private List<TransactionInfo> transactions;

}
