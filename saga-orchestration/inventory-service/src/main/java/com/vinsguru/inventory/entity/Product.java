package com.vinsguru.inventory.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Product {

	@Id
	public String id;

	public Integer idCode;
	public String name;
	public Integer quantity;

	public Product() {
	}

	public Product(Integer idCode, String name, Integer quantity) {
		this.idCode = idCode;
		this.name = name;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format("Product[id=%s, idCode='%s', name='%s', quantity='%s']", id, idCode, name, quantity);
	}

}
