package com.vinsguru.order.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.dto.AddPriceReqRespDTO;
import com.vinsguru.dto.OrderRequestDTO;
import com.vinsguru.dto.OrderResponseDTO;
import com.vinsguru.order.entity.PurchaseOrder;
import com.vinsguru.order.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@Valid @RequestBody OrderRequestDTO requestDTO){
        requestDTO.setOrderId(UUID.randomUUID());
        return this.service.createOrder(requestDTO);
    }

    @GetMapping("/all")
    public List<OrderResponseDTO> getOrders(){
        return this.service.getAll();
    }
    
    @PostMapping("/addPrice")
    public AddPriceReqRespDTO addPrice(@Valid @RequestBody AddPriceReqRespDTO requestDTO){
        return this.service.addProductPrice(requestDTO);
    }
    
    @GetMapping("/allPrices")
    public List<AddPriceReqRespDTO> getAllPrices() {
    	return this.service.getAllPrices();
    }

}
