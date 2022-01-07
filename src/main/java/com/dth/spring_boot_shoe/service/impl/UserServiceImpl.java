package com.dth.spring_boot_shoe.service.impl;

import com.dth.spring_boot_shoe.dto.CommentDTO;
import com.dth.spring_boot_shoe.dto.UserDTO;
import com.dth.spring_boot_shoe.entity.CommentEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import com.dth.spring_boot_shoe.entity.RoleEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.exception.ApiRequestException;
import com.dth.spring_boot_shoe.exception.RequestException;
import com.dth.spring_boot_shoe.repository.CommentRepository;
import com.dth.spring_boot_shoe.repository.ProductRepository;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.request.ChangePWRequest;
import com.dth.spring_boot_shoe.request.InfoRequest;
import com.dth.spring_boot_shoe.request.UserRequest;
import com.dth.spring_boot_shoe.security.provider.Provider;
import com.dth.spring_boot_shoe.security.user.CustomOAuth2User;
import com.dth.spring_boot_shoe.service.RoleService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
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
    public UserRequest loadUser() {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập","login"));
        UserRequest request=modelMapper.map(user,UserRequest.class);
        String address=user.getAddress();
        String[] arrOfStr;
        if(address!=null){
            arrOfStr= user.getAddress().split("-");
            request.setProvince(arrOfStr[0]);
            request.setDistrict(arrOfStr[1]);
            request.setWard(arrOfStr[2]);
            request.setVillage(arrOfStr[3]);
        }
        return request;
    }

    @Override
    public void update(InfoRequest request) {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                        .orElseThrow(()->new ApiRequestException("Qua thời gian chờ, hãy đăng nhập lại"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(request.getLastName()+" "+request.getFirstName());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        userRepository.save(user);
        Authentication authentication= new PreAuthenticatedAuthenticationToken(CustomOAuth2User.createCustomUser(user),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //khi load page info
    @Override
    public UserEntity profile() {
        return userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),1)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập tài khoản","login"));
    }

    //load form detail
    @Override
    public UserEntity profileDetail() {
        return userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),
                1).orElseThrow(()->new ApiRequestException("Bạn chưa đăng nhập tài khoản"));
    }

    @Override
    public Map<String,String> changePass(ChangePWRequest request) {
        HashMap<String,String> map=new HashMap<>();
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),
                1).orElseThrow(()->new ApiRequestException("Bạn hãy đăng nhập lại tài khoản"));
        boolean flag=passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(flag){
            user.setPassword(passwordEncoder.encode(request.getPassword_new()));
            userRepository.save(user);
            map.put("success","Thay đổi mật khẩu thành công, hãy đăng nhập lại");
            return map;
        }
        map.put("error","Mật khẩu không chính xác");
        return map;
    }

    @Override
    public Map<String,String> createComment(CommentDTO commentDTO) {
        UserEntity user=userRepository.findByEmailAndStatus(SecurityContextHolder.getContext().getAuthentication().getName(),
                1).orElseThrow(()->new ApiRequestException("Bạn hãy đăng nhập lại tài khoản"));
        ProductEntity product=productRepository.findById(commentDTO.getProductId()).get();
        CommentEntity comment=modelMapper.map(commentDTO,CommentEntity.class);
        comment.setUser(user);
        comment.setProduct(product);
        commentRepository.save(comment);
        HashMap<String,String> map=new HashMap<>();
        map.put("success","Cảm ơn bạn đã đánh giá");
        return map;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        UserEntity user=userRepository.findByEmailAndStatus(email,1)
                .orElseThrow(()->new UsernameNotFoundException("User này chưa được đăng ký"));
        return CustomOAuth2User.createCustomUser(user);
    }
}
