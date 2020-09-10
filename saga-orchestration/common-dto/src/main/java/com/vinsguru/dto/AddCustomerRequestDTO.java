package com.vinsguru.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddCustomerRequestDTO {
	
	@NotNull
	public Integer dni;
	@NotBlank
	public String firstName;
	@NotBlank
	public String lastName;
	public Double balance;
	
}
