package com.vinsguru.payment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.dto.AddCustomerRequestDTO;
import com.vinsguru.dto.CustomerDTO;
import com.vinsguru.dto.PaymentRequestDTO;
import com.vinsguru.dto.PaymentResponseDTO;
import com.vinsguru.payment.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/debit")
    public PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO){
        return this.service.debit(requestDTO);
    }

    @PostMapping("/credit")
    public PaymentResponseDTO credit(@RequestBody PaymentRequestDTO requestDTO){
        return this.service.credit(requestDTO);
    }
    
    @PostMapping("/addCustomer")
    public PaymentResponseDTO addCustomer(@Valid @RequestBody AddCustomerRequestDTO requestDTO){
        return this.service.addCustomer(requestDTO);
    }
    
    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
    	return this.service.getAllCustomers();
    }

}
