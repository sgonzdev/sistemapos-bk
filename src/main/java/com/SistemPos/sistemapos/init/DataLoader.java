package com.SistemPos.sistemapos.init;
import com.SistemPos.sistemapos.persistence.entity.EPermisos;
import com.SistemPos.sistemapos.persistence.entity.ERoles;
import com.SistemPos.sistemapos.persistence.entity.PermissionEntity;
import com.SistemPos.sistemapos.persistence.entity.RolEntity;
import com.SistemPos.sistemapos.repository.PermissionRepository;
import com.SistemPos.sistemapos.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository; // Tu repositorio de roles

    @Autowired
    private PermissionRepository permissionRepository; // Tu repositorio de permisos

    @Override
    public void run(String... args) throws Exception {
        // Verifica si los permisos ya existen, si no, créalos
        if (permissionRepository.count() == 0) {
            // Crear permisos
            for (EPermisos permiso : EPermisos.values()) {
                PermissionEntity permisoEntity = new PermissionEntity();
                permisoEntity.setName(permiso);
                permissionRepository.save(permisoEntity);
            }
        }

        // Verifica si los roles ya existen, si no, créalos
        if (roleRepository.count() == 0) {
            // Crear roles con permisos
            PermissionEntity gestionarProductos = permissionRepository.findByName(EPermisos.GESTIONAR_PRODUCTOS);
            PermissionEntity verInventario = permissionRepository.findByName(EPermisos.VER_INVENTARIO);
            PermissionEntity gestionarVentas = permissionRepository.findByName(EPermisos.GESTIONAR_VENTAS);
            PermissionEntity verReportes = permissionRepository.findByName(EPermisos.VER_REPORTES);

            // Crear roles
            RolEntity adminRole = new RolEntity();
            adminRole.setName(ERoles.ROLE_ADMIN);
            adminRole.setPermissionsList(new HashSet<>(Arrays.asList(gestionarProductos, verInventario, gestionarVentas, verReportes)));

            RolEntity supervisorRole = new RolEntity();
            supervisorRole.setName(ERoles.ROLE_SUPERVISOR);
            supervisorRole.setPermissionsList(new HashSet<>(Arrays.asList(verInventario, verReportes)));

            RolEntity cashierRole = new RolEntity();
            cashierRole.setName(ERoles.ROLE_CAJERO);
            cashierRole.setPermissionsList(new HashSet<>(Arrays.asList(gestionarVentas, verInventario)));

            roleRepository.saveAll(Arrays.asList(adminRole, cashierRole, supervisorRole));
        }
    }
}
