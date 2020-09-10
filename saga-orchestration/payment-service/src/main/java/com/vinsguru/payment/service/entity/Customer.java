package com.vinsguru.payment.service.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Customer {

	@Id
	public String id;

	public Integer dni;
	public String firstName;
	public String lastName;
	public Double balance;

	public Customer() {}

	public Customer(Integer dni, String firstName, String lastName, Double balance) {
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%s, dni='%s', firstName='%s', lastName='%s', balance='%s']",
				id, dni, firstName, lastName, balance);
	}

}

