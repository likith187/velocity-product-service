package com.example.product.models.api;

import com.example.product.entities.Supplier;

import lombok.Data;

@Data
public class SupplierResponse {
	private Long id;
	private String name;
	private String contactInformation;

	public static SupplierResponse from(Supplier supplier) {
		SupplierResponse response = new SupplierResponse();
		response.id = supplier.getId();
		response.name = supplier.getName();
		response.contactInformation = supplier.getContactInformation();
		return response;
	}

}
