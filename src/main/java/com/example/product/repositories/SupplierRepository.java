package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}