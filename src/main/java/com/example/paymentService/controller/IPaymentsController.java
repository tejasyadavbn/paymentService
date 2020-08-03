package com.example.paymentService.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.paymentService.model.response.ServiceResponse;

@Component
public interface IPaymentsController {

	// method to return response status, time and uniqueId for all the responses
	default ResponseEntity<ServiceResponse> getFinalResponse(Object res) {

		ServiceResponse response = new ServiceResponse();
		response.setDate(LocalDateTime.now().toString());
		response.setResponseId(Long.toString(Math.round(Math.random() * 100000000)));
		response.setStatus("SUCCESS");
		response.setData(res);

		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}

}
