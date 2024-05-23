package com.example.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.entities.Product;
import com.example.product.exception.DataNotFoundException;
import com.example.product.exception.ForbiddenException;
import com.example.product.models.api.CreateOrUpdateProduct;
import com.example.product.models.api.ProductResponse;
import com.example.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "APIs for product requirements")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@Operation(summary = "Create a product", description = "Returns the product that was created. Populate supplier.id if you want to use "
			+ "an existing supplier. Keep it null or remove the field if a new supplier has to be created")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"id\": 7,\n"
					+ "    \"name\": \"Soap\",\n"
					+ "    \"supplier\": {\n"
					+ "        \"id\": 1,\n"
					+ "        \"name\": \"Dove\",\n"
					+ "        \"contactInformation\": \"dove.com\"\n"
					+ "    },\n"
					+ "    \"price\": 45,\n"
					+ "    \"stockQuantity\": 30,\n"
					+ "    \"images\": [\n"
					+ "        \"http://example.com/test\"\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"BAD_REQUEST\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:47:45\",\n"
					+ "    \"message\": \"validation errors\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": [\n"
					+ "        {\n"
					+ "            \"object\": \"createOrUpdateProduct\",\n"
					+ "            \"field\": \"price\",\n"
					+ "            \"message\": \"must not be null\"\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"object\": \"createOrUpdateProduct\",\n"
					+ "            \"field\": \"stockQuantity\",\n"
					+ "            \"message\": \"must not be null\"\n"
					+ "        }\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) })
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateOrUpdateProduct product) throws DataNotFoundException {
		return ResponseEntity.ok(ProductResponse.from(productService.createOrUpdateProduct(product.toProduct())));
	}

	@Operation(summary = "Update a product", description = "Returns the product that was Updated. Populate supplier.id if you want to use "
			+ "an existing supplier. Keep it null or remove the field if a new supplier has to be created")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"id\": 5,\n"
					+ "    \"name\": \"Toothbrush\",\n"
					+ "    \"supplier\": {\n"
					+ "        \"id\": 1,\n"
					+ "        \"name\": \"Colgate\",\n"
					+ "        \"contactInformation\": \"colgate.com\"\n"
					+ "    },\n"
					+ "    \"price\": 45,\n"
					+ "    \"stockQuantity\": 30,\n"
					+ "    \"images\": [\n"
					+ "        \"http://example.com/test\"\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"BAD_REQUEST\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:47:45\",\n"
					+ "    \"message\": \"validation errors\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": [\n"
					+ "        {\n"
					+ "            \"object\": \"createOrUpdateProduct\",\n"
					+ "            \"field\": \"price\",\n"
					+ "            \"message\": \"must not be null\"\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"object\": \"createOrUpdateProduct\",\n"
					+ "            \"field\": \"stockQuantity\",\n"
					+ "            \"message\": \"must not be null\"\n"
					+ "        }\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"NOT_FOUND\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:55:23\",\n"
					+ "    \"message\": \"Product 5 not found\",\n"
					+ "    \"details\": \"uri=/api/v1/products/5\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) })
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateProduct updateProduct) throws DataNotFoundException {
		Optional<Product> existingProduct = productService.viewProductDetails(id);
		if (existingProduct.isPresent()) {
			existingProduct.get().update(updateProduct.toProduct());
			Product product = existingProduct.get();
			Product updatedProduct = productService.createOrUpdateProduct(product);
			return ResponseEntity.ok(ProductResponse.from(updatedProduct));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Delete a product", description = "Deletes the product with the mentioned id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation", content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"NOT_FOUND\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:55:23\",\n"
					+ "    \"message\": \"Product 5 not found\",\n"
					+ "    \"details\": \"uri=/api/v1/products/5\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws DataNotFoundException {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "View all products", description = "Returns the details of products, filters will be applied accordingly, if no filters"
			+ " are mentioned then all products will be returned")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(example = "[\n"
					+ "    {\n"
					+ "        \"id\": 1,\n"
					+ "        \"name\": \"Soap\",\n"
					+ "        \"supplier\": {\n"
					+ "            \"id\": 1,\n"
					+ "            \"name\": \"Dove\",\n"
					+ "            \"contactInformation\": \"dove.com\"\n"
					+ "        },\n"
					+ "        \"price\": 45.00,\n"
					+ "        \"stockQuantity\": 30,\n"
					+ "        \"images\": [\n"
					+ "            \"http://example.com/test\"\n"
					+ "        ]\n"
					+ "    },\n"
					+ "    {\n"
					+ "        \"id\": 2,\n"
					+ "        \"name\": \"Soap\",\n"
					+ "        \"supplier\": {\n"
					+ "            \"id\": 2,\n"
					+ "            \"name\": \"Dove\",\n"
					+ "            \"contactInformation\": \"dove.com\"\n"
					+ "        },\n"
					+ "        \"price\": 45.00,\n"
					+ "        \"stockQuantity\": 30,\n"
					+ "        \"images\": [\n"
					+ "            \"http://example.com/test\"\n"
					+ "        ]\n"
					+ "    }\n"
					+ "]"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"BAD_REQUEST\",\n"
					+ "    \"timestamp\": \"22-05-2024 10:08:50\",\n"
					+ "    \"message\": \"minPrice is invalid for value a\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) })
	@GetMapping
	public ResponseEntity<List<ProductResponse>> listProducts(@RequestParam(required = false) Long supplierId,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
		List<Product> products = productService.filterProductsBySupplierAndPriceRange(supplierId, minPrice, maxPrice);
		return ResponseEntity.ok(products.stream().map(ProductResponse::from).toList());
	}

	@Operation(summary = "View details of a product", description = "Returns the details of the product requested")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"id\": 1,\n"
					+ "    \"name\": \"Soap\",\n"
					+ "    \"supplier\": {\n"
					+ "        \"id\": 1,\n"
					+ "        \"name\": \"Dove\",\n"
					+ "        \"contactInformation\": \"dove.com\"\n"
					+ "    },\n"
					+ "    \"price\": 45.00,\n"
					+ "    \"stockQuantity\": 30,\n"
					+ "    \"images\": [\n"
					+ "        \"http://example.com/test\"\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"BAD_REQUEST\",\n"
					+ "    \"timestamp\": \"22-05-2024 10:08:50\",\n"
					+ "    \"message\": \"minPrice is invalid for value a\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"NOT_FOUND\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:55:23\",\n"
					+ "    \"message\": \"Product 5 not found\",\n"
					+ "    \"details\": \"uri=/api/v1/products/5\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) })
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> viewProductDetails(@PathVariable Long id) throws DataNotFoundException {
		Optional<Product> product = productService.viewProductDetails(id);
		return product.map(e -> ResponseEntity.ok(ProductResponse.from(e))).orElseThrow(() ->
		new DataNotFoundException("Product " + id + " not found"));
	}

	@Operation(summary = "Adjusts stock for a product.", description = "Returns a product after adjusting the stock")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"id\": 1,\n"
					+ "    \"name\": \"Soap\",\n"
					+ "    \"supplier\": {\n"
					+ "        \"id\": 1,\n"
					+ "        \"name\": \"Dove\",\n"
					+ "        \"contactInformation\": \"dove.com\"\n"
					+ "    },\n"
					+ "    \"price\": 45.00,\n"
					+ "    \"stockQuantity\": 2030,\n"
					+ "    \"images\": [\n"
					+ "        \"http://example.com/test\"\n"
					+ "    ]\n"
					+ "}"))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"BAD_REQUEST\",\n"
					+ "    \"timestamp\": \"23-05-2024 12:49:21\",\n"
					+ "    \"message\": \"Required request parameter 'quantity' for method parameter type Long is not present\",\n"
					+ "    \"details\": \"uri=/api/v1/products/7/adjust-stock\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"FORBIDDEN\",\n"
					+ "    \"timestamp\": \"23-05-2024 11:14:24\",\n"
					+ "    \"message\": \"Out of stock\",\n"
					+ "    \"details\": \"uri=/api/v1/products/1/adjust-stock\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"NOT_FOUND\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:55:23\",\n"
					+ "    \"message\": \"Product 5 not found\",\n"
					+ "    \"details\": \"uri=/api/v1/products/5\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}}"))),
			@ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content(schema = @Schema(example = "{\n"
					+ "    \"status\": \"INTERNAL_SERVER_ERROR\",\n"
					+ "    \"timestamp\": \"22-05-2024 09:44:32\",\n"
					+ "    \"message\": \"something went wrong\",\n"
					+ "    \"details\": \"uri=/api/v1/products\",\n"
					+ "    \"validationErrors\": []\n"
					+ "}"))) 
			})
	@PatchMapping("/{id}/adjust-stock")
	public ResponseEntity<ProductResponse> adjustStock(@PathVariable Long id, @Min(-10000000) @Max(10000000) @NotNull @RequestParam Long quantity) throws DataNotFoundException, ForbiddenException {
		Optional<Product> optional = productService.viewProductDetails(id);
		if (optional.isPresent()) {
			Product product = optional.get();
			product.setStockQuantity(product.getStockQuantity() + quantity);
			if (product.getStockQuantity() < 0) {
				throw new ForbiddenException("Out of stock");
			}
			productService.createOrUpdateProduct(product);
			return ResponseEntity.ok(ProductResponse.from(product));
		}
		throw new DataNotFoundException("Product " + id + " not found");
	}
}