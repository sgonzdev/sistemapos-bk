package com.SistemPos.sistemapos.repository;

import com.SistemPos.sistemapos.persistence.entity.ERoles;
import com.SistemPos.sistemapos.persistence.entity.RolEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RolEntity, Long> {
    RolEntity findByName(ERoles name);
}
