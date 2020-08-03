package com.example.paymentService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PREVIOUSSTATEMENT")
@Data
public class PreviousStatement {

	@Column(name = "TITLE")
	private String title;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "DATE")
	private String date;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "CARDUSED")
	private String cardUsed;

	@Column(name = "CATEGORY")
	private String category;

	@Id
	@Column(name = "REFERENCENUMBER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String referenceNumber;

	@Column(name = "STATEMENTPERIOD")
	private String statementPeriod;

	@Column(name = "CUSTOMERID")
	private String customerId;

}
