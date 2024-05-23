package com.example.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.product.entities.Product;
import com.example.product.entities.Supplier;
import com.example.product.exception.DataNotFoundException;
import com.example.product.models.api.CreateOrUpdateProduct;
import com.example.product.models.api.CreateOrUpdateSupplier;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@MockBean
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateProduct() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Product product = new Product(1L, "Soap", new Supplier(1L, "Dove", "dove.com"), new BigDecimal(45.0), 30,
				Arrays.asList("http://example.com/test"));
		CreateOrUpdateProduct createProduct = new CreateOrUpdateProduct("Soap",
				new CreateOrUpdateSupplier(1L, "Dove", "dove.com"), new BigDecimal(45.0), 30,
				Arrays.asList("http://example.com/test"));
		when(productService.createOrUpdateProduct(any(Product.class))).thenReturn(product);

		mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(createProduct)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Soap"));
	}

	@Test
	void testUpdateProduct() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Product product = new Product(1L, "Toothbrush", new Supplier(1L, "Colgate", "colgate.com"),
				new BigDecimal(45.0), 30, Arrays.asList("http://example.com/test"));
		CreateOrUpdateProduct updateProduct = new CreateOrUpdateProduct("Toothbrush",
				new CreateOrUpdateSupplier(1L, "Colgate", "colgate.com"), new BigDecimal(45.0), 30,
				Arrays.asList("http://example.com/test"));
		when(productService.viewProductDetails(anyLong())).thenReturn(Optional.of(product));
		when(productService.createOrUpdateProduct(any(Product.class))).thenReturn(product);

		mockMvc.perform(put("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updateProduct)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Toothbrush"));
	}

	@Test
	void testDeleteProduct() throws Exception {
		Product product = new Product(1L, "Toothbrush", new Supplier(1L, "Colgate", "colgate.com"),
				new BigDecimal(45.0), 30, Arrays.asList("http://example.com/test"));
		doNothing().when(productService).deleteProduct(product.getId());
		mockMvc.perform(delete("/api/v1/products/1")).andExpect(status().isNoContent());
		doThrow(new DataNotFoundException("")).when(productService).deleteProduct(product.getId());
		mockMvc.perform(delete("/api/v1/products/1")).andExpect(status().isNotFound());
	}

	@Test
	void testViewProductDetails() throws Exception {
		Product product = new Product(1L, "Toothbrush", new Supplier(1L, "Colgate", "colgate.com"),
				new BigDecimal(45.0), 30, Arrays.asList("http://example.com/test"));
		when(productService.viewProductDetails(product.getId())).thenReturn(Optional.of(product));

		mockMvc.perform(get("/api/v1/products/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(product.getId())).andExpect(jsonPath("$.name").value("Toothbrush"));
		mockMvc.perform(get("/api/v1/products/2")).andExpect(status().isNotFound());
	}

	@Test
	void testViewAllProducts() throws Exception {
		Product product1 = new Product(1L, "Shampoo", new Supplier(2L, "Dove", "dove.com"), new BigDecimal(10.0), 20,
				Arrays.asList("http://example.com/test"));
		Product product2 = new Product(2L, "Conditioner", new Supplier(2L, "Dove", "dove.com"), new BigDecimal(12.0),
				25, Arrays.asList("http://example.com/test"));
		Product product3 = new Product(3L, "Face wash", new Supplier(3L, "Himalaya", "himalaya.com"),
				new BigDecimal(20.0), 25, Arrays.asList("http://example.com/test"));
		when(productService.filterProductsBySupplierAndPriceRange(null, null, null))
				.thenReturn(List.of(product1, product2, product3));
		when(productService.filterProductsBySupplierAndPriceRange(3L, null, null)).thenReturn(List.of(product3));
		when(productService.filterProductsBySupplierAndPriceRange(null, 15.0, 25.0)).thenReturn(List.of(product3));

		mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(product1.getId()))
				.andExpect(jsonPath("$[0].name").value("Shampoo"))
				.andExpect(jsonPath("$[1].id").value(product2.getId()))
				.andExpect(jsonPath("$[1].name").value("Conditioner"));

		mockMvc.perform(get("/api/v1/products").param("supplierId", "3")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(product3.getId()))
				.andExpect(jsonPath("$[0].name").value("Face wash"));
		mockMvc.perform(get("/api/v1/products").param("supplierId", "4")).andExpect(status().isOk())
				.andExpect(content().json("[]"));
		mockMvc.perform(get("/api/v1/products").param("minPrice", "15").param("maxPrice", "25"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(product3.getId()))
				.andExpect(jsonPath("$[0].name").value("Face wash"));
	}
}
