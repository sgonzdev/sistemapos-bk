package com.SistemPos.sistemapos.init;
import com.SistemPos.sistemapos.models.permission.EPermisos;
import com.SistemPos.sistemapos.models.roles.ERoles;
import com.SistemPos.sistemapos.models.permission.PermissionEntity;
import com.SistemPos.sistemapos.models.roles.RolEntity;
import com.SistemPos.sistemapos.repository.PermissionRepository;
import com.SistemPos.sistemapos.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DataLoader(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        loadPermissions();
        loadRoles();
    }

    private void loadPermissions() {
        if (permissionRepository.count() == 0) {
            Arrays.stream(EPermisos.values())
                    .map(PermissionEntity::new)
                    .forEach(permissionRepository::save);
        }
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Map<ERoles, Set<EPermisos>> rolePermissions = Map.of(
                    ERoles.ADMIN, Set.of(EPermisos.GESTIONAR_PRODUCTOS, EPermisos.VER_INVENTARIO, EPermisos.GESTIONAR_VENTAS, EPermisos.VER_REPORTES),
                    ERoles.SUPERVISOR, Set.of(EPermisos.VER_INVENTARIO, EPermisos.VER_REPORTES),
                    ERoles.CAJERO, Set.of(EPermisos.GESTIONAR_VENTAS, EPermisos.VER_INVENTARIO),
                    ERoles.CLIENTE, Set.of(EPermisos.VER_INVENTARIO)
            );

            rolePermissions.forEach((role, permission) -> {
                RolEntity roleEntity = new RolEntity();
                roleEntity.setName(role);
                roleEntity.setPermissionsList(getPermissionEntities(permission));
                roleRepository.save(roleEntity);
            });
        }
    }

    private Set<PermissionEntity> getPermissionEntities(Set<EPermisos> permission) {
        return permission.stream()
                .map(permissionRepository::findByName)
                .collect(Collectors.toSet());
    }
}