package com.example.paymentService.configuration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentsDBConfiguration {

	@Bean
	public DataSource dataSource() {

		DataSourceBuilder dsb = DataSourceBuilder.create();
		dsb.url("jdbc:mysql://localhost:3306/paymentSystem?serverTimezone=America/New_York");
		dsb.username("root");
		dsb.password("rootuser123");

		return dsb.build();
	}

}
