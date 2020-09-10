package com.vinsguru.dto;

import java.util.UUID;

import com.vinsguru.enums.InventoryStatus;

import lombok.Data;

@Data
public class InventoryResponseDTO {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;
    private String name;
    private Integer quantity;

}
