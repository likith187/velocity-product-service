package com.example.product.entities;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Getter
@Setter
public class Product {

    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @NotNull
    @Min(1)
    @Max(1000000)
    private BigDecimal price;
    
    @Min(0)
    @Max(10000000)
    private long stockQuantity;
    
    @Size(max = 10)
    @ElementCollection
    private List<String> images;
    
    @Hidden
    @Version
    private Long version;

	public Product(Long id, @NotBlank String name, @NotNull Supplier supplier,
			@NotNull @Min(1) @Max(1000000) BigDecimal price, @Min(0) @Max(10000000) long stockQuantity,
			@Size(max = 10) List<String> images) {
		this.id = id;
		this.name = name;
		this.supplier = supplier;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.images = images;
	}
	
	public Product() {}
    
    public void update(Product product) {
		name = product.getName();
		supplier = product.getSupplier();
		price = product.getPrice();
		stockQuantity = product.getStockQuantity();
		images = product.getImages();
	}
}
