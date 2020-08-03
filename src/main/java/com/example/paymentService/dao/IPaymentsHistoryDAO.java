package com.example.paymentService.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.example.paymentService.entity.CurrentStatement;
import com.example.paymentService.entity.PreviousStatement;

@Component
public interface IPaymentsHistoryDAO extends CrudRepository<CurrentStatement, Long> {

	@Query("SELECT t FROM CurrentStatement t WHERE t.customerId = :custId AND t.statementPeriod =:tRange")
	public List<CurrentStatement> getCurrentPaymentHistory(@Param("custId") String customerId,
			@Param("tRange") String timeRange);

	@Query("SELECT t FROM PreviousStatement t WHERE t.customerId = :custId AND t.statementPeriod =:tRange")
	public List<PreviousStatement> getPreviousPaymentHistory(@Param("custId") String customerId,
			@Param("tRange") String timeRange);

	@Query("SELECT t FROM PreviousStatement t WHERE t.customerId = :custId")
	public List<PreviousStatement> getAllPreviousPaymentHistory(@Param("custId") String customerId);

}
