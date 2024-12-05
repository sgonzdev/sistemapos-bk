package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.persistence.entity.EPermisos;
import com.SistemPos.sistemapos.persistence.entity.PermissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {
    PermissionEntity findByName(EPermisos name);
}
