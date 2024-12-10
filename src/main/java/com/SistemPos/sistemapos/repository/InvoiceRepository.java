package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.models.invoice.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
