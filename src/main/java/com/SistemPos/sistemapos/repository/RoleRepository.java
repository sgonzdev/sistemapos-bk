package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.models.roles.ERoles;
import com.SistemPos.sistemapos.models.roles.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByName(ERoles name);
}
