package com.example.paymentService.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.example.paymentService.error.PaymentServiceError;

@Component
public class PaymentServiceFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// Authenticate username and password
		HttpServletRequest request = (HttpServletRequest) req;
		if (!(request.getHeader("userName").equalsIgnoreCase("user")
				&& request.getHeader("password").equalsIgnoreCase("password"))) {
			throw new PaymentServiceError("Invalid User Credentials");
		}
		chain.doFilter(request, res);
	}

}
