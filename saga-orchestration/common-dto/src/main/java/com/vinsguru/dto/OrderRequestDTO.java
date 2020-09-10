package com.vinsguru.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderRequestDTO {

	@NotNull
    private Integer userId;
	@NotNull
    private Integer productId;
    private UUID orderId;

}