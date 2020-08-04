package com.example.paymentService.async;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class AsyncCommandInvoker implements InitializingBean {

	public Map<String, Object> invokeAsync(List<AsyncCommandTask> asyncCommandTasks, int timeout) {

		List<NamedCompletableFuture> namedFs = asyncCommandTasks.stream().map(this::intoNamedCompletableFuture)
				.collect(Collectors.toList());

		Map<String, Object> collect = namedFs.stream().collect(Collectors.toMap(namedF -> namedF.getName(), namedF -> {
			try {
				return namedF.getCompletableFuture().get(timeout, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			} catch (TimeoutException e) {
				throw new RuntimeException(e);
			}

		}));
		return collect;

	}

	private NamedCompletableFuture intoNamedCompletableFuture(AsyncCommandTask asyncCommandTask) {
		return NamedCompletableFuture.builder().name(asyncCommandTask.getCommandName())
				.completableFuture(CompletableFuture.supplyAsync(asyncCommandTask.getAsyncSupplier())).build();
	}

	/*
	 * private ExecutorService traceableExecutorServiceInstance(String sName) {
	 * return new ExecutorService(); }
	 */

	private static AsyncCommandInvoker instance;

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = this;

	}

	public static AsyncCommandInvoker getInstance() {
		return instance;
	}

}
