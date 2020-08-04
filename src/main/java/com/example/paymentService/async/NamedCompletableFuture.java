package com.example.paymentService.async;

import java.util.concurrent.CompletableFuture;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NamedCompletableFuture<T> {

	private String name;
	private CompletableFuture<T> completableFuture;

}
