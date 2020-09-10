package com.vinsguru.dto;

import javax.validation.constraints.NotNull;

import com.vinsguru.enums.ProductPriceStatus;

import lombok.Data;

@Data
public class AddPriceReqRespDTO {
	
	@NotNull
	private Integer productId;
	@NotNull
	private Double unitPrice;
	private ProductPriceStatus status;

}
