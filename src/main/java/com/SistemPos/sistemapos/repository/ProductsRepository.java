package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.models.products.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
    boolean existsByName(String name);
}
