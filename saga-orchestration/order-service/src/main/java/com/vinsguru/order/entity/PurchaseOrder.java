package com.vinsguru.order.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.vinsguru.enums.OrderStatus;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {

    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;
    private String error;

}