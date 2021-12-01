package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity save(UserDTO userDTO) throws Exception;
    UserEntity findByEmail(String email);
}
