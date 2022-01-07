package com.dth.spring_boot_shoe.service;

import com.dth.spring_boot_shoe.dto.CommentDTO;
import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.request.ChangePWRequest;
import com.dth.spring_boot_shoe.request.InfoRequest;
import com.dth.spring_boot_shoe.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserEntity save(UserDTO userDTO);
    UserEntity findByEmail(String email);
    UserRequest loadUser();

    void update(InfoRequest request);

    UserEntity profile();

    UserEntity profileDetail();

    Map<String,String> changePass(ChangePWRequest request);

    Map<String,String> createComment(CommentDTO commentDTO);

}
