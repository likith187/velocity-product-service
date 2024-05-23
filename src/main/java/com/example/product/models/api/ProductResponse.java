package com.example.product.models.api;

import java.math.BigDecimal;
import java.util.List;

import com.example.product.entities.Product;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private SupplierResponse supplier;
    private BigDecimal price;
    private Long stockQuantity;
    private List<String> images;
    
    public static ProductResponse from(Product product) {
    	ProductResponse response = new ProductResponse();
    	response.setId(product.getId());
    	response.setName(product.getName());
    	response.setPrice(product.getPrice());
    	response.setStockQuantity(product.getStockQuantity());
    	response.setImages(product.getImages());
    	response.setSupplier(SupplierResponse.from(product.getSupplier()));
    	return response;
    }
}
