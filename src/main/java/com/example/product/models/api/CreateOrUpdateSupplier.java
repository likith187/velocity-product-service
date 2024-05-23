package com.example.product.models.api;

import com.example.product.entities.Supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrUpdateSupplier {
    private Long id;
    
    @Schema(example = "Dove")
    @NotBlank
    private String name;

    @Schema(example = "dove.com")
    @NotBlank
    private String contactInformation;
    
    
    public Supplier toSupplier() {
    	Supplier supplier = new Supplier();
    	supplier.setId(id);
    	supplier.setName(name);
    	supplier.setContactInformation(contactInformation);
    	return supplier;
    }

}
