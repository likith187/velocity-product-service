package com.example.product.entities;

import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String contactInformation;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Product> products;
    
    @Hidden
    @Version
    private Long version;

	public Supplier(Long id, @NotBlank String name, @NotBlank String contactInformation) {
		super();
		this.id = id;
		this.name = name;
		this.contactInformation = contactInformation;
	}

	public Supplier() {
	}

	public void update(Supplier supplier) {
		name = supplier.getName();
		contactInformation = supplier.getContactInformation();
	}

}