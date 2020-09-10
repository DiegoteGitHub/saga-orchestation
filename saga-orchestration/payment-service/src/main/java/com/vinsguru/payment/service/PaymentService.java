package com.vinsguru.payment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinsguru.dto.AddCustomerRequestDTO;
import com.vinsguru.dto.CustomerDTO;
import com.vinsguru.dto.PaymentRequestDTO;
import com.vinsguru.dto.PaymentResponseDTO;
import com.vinsguru.enums.PaymentStatus;
import com.vinsguru.payment.service.entity.Customer;
import com.vinsguru.payment.service.repository.CustomerRepository;

@Service
public class PaymentService {

	@Autowired
	private CustomerRepository repository;
	
	@PostConstruct
	private void init() {
		repository.deleteAll();
		// save a couple of customers
		//repository.save(new Customer(1,"Alice", "Smith", 300d));
		//repository.save(new Customer(2, "Bob", "Smith", 600d));
	}

	public PaymentResponseDTO debit(final PaymentRequestDTO requestDTO) {
		Optional<Customer> user = repository.findByDni(requestDTO.getUserId());
		double balance = 0d;
		PaymentResponseDTO responseDTO = new PaymentResponseDTO();
		if (user.isPresent()) {
			balance = user.get().getBalance();
			responseDTO.setAmount(requestDTO.getAmount());
			responseDTO.setUserId(requestDTO.getUserId());
			responseDTO.setOrderId(requestDTO.getOrderId());
			responseDTO.setStatus(PaymentStatus.PAYMENT_REJECTED);
			if (balance >= requestDTO.getAmount()) {
				responseDTO.setStatus(PaymentStatus.PAYMENT_APPROVED);
				user.get().setBalance(balance - requestDTO.getAmount());
				repository.save(user.get());
			}
		} else {
			responseDTO.setUserId(requestDTO.getUserId());
			responseDTO.setStatus(PaymentStatus.CUSTOMER_INEXISTENT);
		}
		return responseDTO;
	}

	public PaymentResponseDTO credit(final PaymentRequestDTO requestDTO) {
		Optional<Customer> user = repository.findByDni(requestDTO.getUserId());
		PaymentResponseDTO responseDTO = new PaymentResponseDTO();
		if (user.isPresent()) {
			user.get().setBalance(user.get().getBalance() + requestDTO.getAmount());
			repository.save(user.get());
		} else {
			responseDTO.setUserId(requestDTO.getUserId());
			responseDTO.setStatus(PaymentStatus.CUSTOMER_INEXISTENT);
		}
		return responseDTO;
	}

	public PaymentResponseDTO addCustomer(AddCustomerRequestDTO requestDTO) {
		Optional<Customer> optionalCustomer = repository.findByDni(requestDTO.getDni());
		PaymentResponseDTO responseDTO = new PaymentResponseDTO();
		if (optionalCustomer.isEmpty()) {
			Double balance = requestDTO.getBalance() != null ? requestDTO.getBalance() : 0d;
			Customer c = new Customer(requestDTO.getDni(), requestDTO.getFirstName(), requestDTO.getLastName(), balance);
			repository.save(c);
			responseDTO.setUserId(requestDTO.getDni());
			responseDTO.setStatus(PaymentStatus.CUSTOMER_ADDED);
		} else {
			responseDTO.setUserId(requestDTO.getDni());
			responseDTO.setStatus(PaymentStatus.CUSTOMER_ALREADY_EXISTS);
		}
		return responseDTO;
	}

	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = repository.findAll();
		List<CustomerDTO> custList = new ArrayList<>();
		for (Customer customer: customers) {
			CustomerDTO custDTO = new CustomerDTO();
			custDTO.setBalance(customer.getBalance());
			custDTO.setDni(customer.getDni());
			custDTO.setFirstName(customer.getFirstName());
			custDTO.setLastName(customer.getLastName());
			custList.add(custDTO);
		}
		return custList;
	}

}
