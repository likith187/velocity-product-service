package com.example.product.models.api;

import java.math.BigDecimal;
import java.util.List;

import com.example.product.entities.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrUpdateProduct {

	@Schema(example = "Soap")
	@NotBlank
	private String name;

	@NotNull
	private CreateOrUpdateSupplier supplier;

	@Schema(example = "45")
	@NotNull
	@Min(1)
	@Max(1000000)
	private BigDecimal price;

	@Schema(example = "30")
	@NotNull
	@Min(0)
	@Max(10000000)
	private Integer stockQuantity;
	
	@Schema(example = "[\"http://example.com/test\"]")
	@Size(max = 10)
	private List<String> images;
	
	public Product toProduct() {
		Product product = new Product();
		product.setName(name);
		product.setSupplier(supplier.toSupplier());
		product.setPrice(price);
		product.setStockQuantity(stockQuantity);
		product.setImages(images);
		return product;
	}
}
