package com.dth.spring_boot_shoe.security.service;

import com.dth.spring_boot_shoe.entity.RoleEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.RoleRepository;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.security.provider.Provider;
import com.dth.spring_boot_shoe.security.user.CustomOAuth2User;
import com.dth.spring_boot_shoe.security.user.OAuth2UserInfo;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =super.loadUser(userRequest);
        return processOAuthPostLogin(user,userRequest);
    }

    //Nhận vào thông tin user, kiểm tra xem có tồn tại hay chưa -> chỉnh sửa hoặc thêm mới
    public OAuth2User processOAuthPostLogin(OAuth2User auth2User,OAuth2UserRequest userRequest) {
        Optional<UserEntity> existUser = userRepository.findByEmailAndStatus(auth2User.getAttribute("email"),1);
        UserEntity user;
        OAuth2UserInfo auth2UserInfo=new OAuth2UserInfo(auth2User);
        if (existUser.isPresent()) {
            user=existUser.get();
            user=updateUser(user,auth2UserInfo);
        }else {
            user=createUser(userRequest,auth2UserInfo);
        }
        return CustomOAuth2User.createCustomUser(user,auth2User.getAttributes());
    }

    //Thêm mới thông tin của người dùng
    private UserEntity createUser(OAuth2UserRequest userRequest,OAuth2UserInfo auth2UserInfo){
        UserEntity user=new UserEntity();
        Optional<RoleEntity> role=roleRepository.findByCode("ROLE_USER");
        user.setFullName(auth2UserInfo.getName());
        user.setEmail(auth2UserInfo.getEmail());
        user.setFirstName(auth2UserInfo.getFirstName());
        user.setLastName(auth2UserInfo.getLastName());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setProvider(Provider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        user.setRoles(Arrays.asList(role.get()));
        user.setStatus(1);
        user.setSlug(StringUtils.removeAccent(auth2UserInfo.getName()));
        return userRepository.save(user);
    }

    //chỉnh sửa thông tin người dùng
    private UserEntity updateUser(UserEntity user,OAuth2UserInfo auth2UserInfo){
        user.setFullName(auth2UserInfo.getName());
        user.setSlug(StringUtils.removeAccent(auth2UserInfo.getName()));
        return userRepository.save(user);
    }
}
