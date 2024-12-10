package com.SistemPos.sistemapos.services;

import com.SistemPos.sistemapos.models.user.UserEntity;
import com.SistemPos.sistemapos.repository.UserRespository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServices  implements UserDetailsService {

    private final UserRespository userRespository;

    public UserDetailServices(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity userEntity = userRespository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("The user " + username + " no found."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        userEntity.getRoles().stream().flatMap(role -> role.getPermissionsList().stream()).forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName().name())));


        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnable(), userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(), userEntity.isAccountNonLocked(), authorityList);
    }


}


