package com.example.paymentService.async;

import java.util.function.Supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsyncCommandTask<T> {

	private String commandName;
	private Supplier<T> asyncSupplier;

}
