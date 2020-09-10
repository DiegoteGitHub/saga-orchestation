package com.vinsguru.order.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductPrice {

	@Id
	private String id;
	private Integer productId;
	private Double unitPrice;

	public ProductPrice(Integer productId, Double unitPrice) {
		this.productId = productId;
		this.unitPrice = unitPrice;
	}

}
