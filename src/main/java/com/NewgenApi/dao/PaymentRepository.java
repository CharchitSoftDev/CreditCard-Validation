package com.NewgenApi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.NewgenApi.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment,Long>{

public List<Payment> findByTransactionId(String transactionId);
}