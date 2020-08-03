package com.example.paymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.paymentService.model.request.PaymentHistoryRequest;
import com.example.paymentService.model.response.ServiceResponse;
import com.example.paymentService.service.IPaymentsService;

@Controller
@RequestMapping(value = "/payments")
public class PaymentsController implements IPaymentsController {

	@Autowired
	IPaymentsService paymentsService;

	@RequestMapping(value = "/get-payment-history", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ServiceResponse> getPaymentHistory(@RequestBody PaymentHistoryRequest request) {
		return getFinalResponse(paymentsService.getPaymentHistory(request));
	}

}
