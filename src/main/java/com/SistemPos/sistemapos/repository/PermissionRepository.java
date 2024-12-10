package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.models.permission.EPermisos;
import com.SistemPos.sistemapos.models.permission.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByName(EPermisos name);
}
