package com.vinsguru.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InventoryAddProdRequestDTO {

	@NotNull(message = "Product ID is required")
    private Integer productId;
	@NotBlank(message = "Product name is required")
    private String name;
	private Integer quantity;

}
