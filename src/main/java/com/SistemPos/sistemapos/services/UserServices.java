package com.SistemPos.sistemapos.services;

import com.SistemPos.sistemapos.dto.request.UserRequest;
import com.SistemPos.sistemapos.models.roles.ERoles;
import com.SistemPos.sistemapos.models.roles.RolEntity;
import com.SistemPos.sistemapos.models.user.UserEntity;
import com.SistemPos.sistemapos.models.user.UserProfileEntity;
import com.SistemPos.sistemapos.repository.RoleRepository;
import com.SistemPos.sistemapos.repository.UserRespository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServices {

    private final UserRespository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServices(UserRespository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public UserEntity saveUser(UserRequest userRequest) {
        UserEntity userEntity = createUserEntityFromRequest(userRequest);
        encodeAndSetUserPassword(userEntity);
        setUserDefaultValues(userEntity);

        Set<RolEntity> roleEntities = fetchRolesFromNames(userRequest.getRoles());
        userEntity.setRoles(roleEntities);

        if (userRequest.getUserProfile() != null) {
            UserProfileEntity userProfileEntity = mapToUserProfileEntity(userRequest.getUserProfile());
            linkUserProfile(userEntity, userProfileEntity);
        }

        return userRepository.save(userEntity);
    }

    private UserEntity createUserEntityFromRequest(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        return userEntity;
    }

    private void encodeAndSetUserPassword(UserEntity userEntity) {
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
    }

    private void setUserDefaultValues(UserEntity userEntity) {
        userEntity.setEnable(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
    }

    private Set<RolEntity> fetchRolesFromNames(List<String> roles) {
        return roles.stream()
                .map(roleName -> {
                    String roleEnumValue = roleName.toUpperCase();
                    if (!isValidEnum(ERoles.class, roleEnumValue)) {
                        throw new IllegalArgumentException("Role not valid: " + roleName);
                    }
                    return roleRepository.findByName(ERoles.valueOf(roleEnumValue))
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                })
                .collect(Collectors.toSet());
    }

    private <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumValue) {
        for (E constant : enumClass.getEnumConstants()) {
            if (constant.name().equals(enumValue)) {
                return true;
            }
        }
        return false;
    }

    private UserProfileEntity mapToUserProfileEntity(UserProfileEntity userProfile) {
        UserProfileEntity userProfileEntity = new UserProfileEntity();
        userProfileEntity.setFirstName(userProfile.getFirstName());
        userProfileEntity.setLastName(userProfile.getLastName());
        userProfileEntity.setEmail(userProfile.getEmail());
        userProfileEntity.setPhoneNumber(userProfile.getPhoneNumber());
        userProfileEntity.setAddress(userProfile.getAddress());
        return userProfileEntity;
    }

    private void linkUserProfile(UserEntity userEntity, UserProfileEntity userProfileEntity) {
        userEntity.setProfile(userProfileEntity);
    }



}
