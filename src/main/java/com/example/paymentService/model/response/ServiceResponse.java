package com.example.paymentService.model.response;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class ServiceResponse {

	private String date;
	private String responseId;
	private String status;
	private Object data;

	// private ErrorDetails errorDetails;

}
