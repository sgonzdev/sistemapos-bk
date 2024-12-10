package com.SistemPos.sistemapos.dto.request;

import com.SistemPos.sistemapos.models.user.UserProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserRequest {
    private String username;
    private String password;
    private List<String> roles;
    private UserProfileEntity userProfile;
}
