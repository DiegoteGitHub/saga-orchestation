package com.vinsguru.payment.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinsguru.payment.service.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {
	public List<Customer> findByFirstName(String firstName);
	public List<Customer> findByLastName(String lastName);
	public Optional<Customer> findByDni(Integer dni);
}
