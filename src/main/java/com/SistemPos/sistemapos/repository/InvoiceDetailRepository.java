package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.models.invoice.InvoiceDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Long> {
}
