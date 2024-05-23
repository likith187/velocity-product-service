package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.product.entities.Supplier;
import com.example.product.exception.DataNotFoundException;
import com.example.product.repositories.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    
	public Supplier createOrUpdateSupplier(Supplier supplier) throws DataNotFoundException {
		if (supplier.getId() != null) {
			Optional<Supplier> existingSupplier = supplierRepository.findById(supplier.getId());
			if (existingSupplier.isPresent()) {
				existingSupplier.get().update(supplier);
				supplier = existingSupplier.get();
			} else {
				throw new DataNotFoundException("Supplier " + supplier.getId() + " not found");
			}
		}
		return supplierRepository.save(supplier);
    }

    public List<Supplier> listSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplier(Long id) {
        return supplierRepository.findById(id);
    }
}
