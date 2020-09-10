package com.vinsguru.dto;

import java.util.UUID;

import com.vinsguru.enums.InventoryStatus;

import lombok.Data;

@Data
public class InventoryRequestDTO {

    private Integer userId;
    private Integer productId;
    private String name;
    private Double unitPrice;
    private UUID orderId;
    private Integer quantity;
    private InventoryStatus status;

}
