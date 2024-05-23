package com.example.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findBySupplierId(Long supplierId);

	List<Product> findByPriceBetween(double minPrice, double maxPrice);
	
	List<Product> findBySupplierIdAndPriceBetween(Long supplierId, double minPrice, double maxPrice);
}
