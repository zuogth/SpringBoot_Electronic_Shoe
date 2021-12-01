package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.RoleEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.security.provider.Provider;
import com.dth.spring_boot_shoe.security.user.CustomOAuth2User;
import com.dth.spring_boot_shoe.service.RoleService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @Override
    public UserEntity save(UserDTO userDTO){
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ApiRequestException("Email da ton tai");
        }
        RoleEntity role=roleService.findByCode("ROLE_USER");
        UserEntity user=modelMapper.map(userDTO,UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(role));
        user.setProvider(Provider.local);
        return userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailAndStatus(email,1)
                .orElseThrow(()->new ApiRequestException("User này chưa được đăng ký"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmailAndStatus(email,1)
                .orElseThrow(()->new UsernameNotFoundException("User này chưa được đăng ký"));
        return CustomOAuth2User.createCustomUser(user);
    }
}
