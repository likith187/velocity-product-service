package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.product.entities.Product;
import com.example.product.entities.Supplier;
import com.example.product.exception.DataNotFoundException;
import com.example.product.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final SupplierService supplierService;

	@Transactional
	public Product createOrUpdateProduct(@Valid Product product) throws DataNotFoundException {
		Supplier supplier = supplierService.createOrUpdateSupplier(product.getSupplier());
		product.setSupplier(supplier);
		return productRepository.save(product);
	}

	@Transactional
	public void deleteProduct(Long id) throws DataNotFoundException {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
		}
		throw new DataNotFoundException("Product " + id + " not found");
	}

	public Optional<Product> viewProductDetails(Long id) {
		return productRepository.findById(id);
	}

	public List<Product> filterProductsBySupplierAndPriceRange(Long supplierId, Double minPrice, Double maxPrice) {
		if (supplierId != null && minPrice != null && maxPrice != null) {
			return productRepository.findBySupplierIdAndPriceBetween(supplierId, minPrice, maxPrice);
		} else if (supplierId != null) {
			return productRepository.findBySupplierId(supplierId);
		} else if (minPrice != null && maxPrice != null) {
			return productRepository.findByPriceBetween(minPrice, maxPrice);
		} else {
			return productRepository.findAll();
		}
	}
}