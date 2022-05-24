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
import com.dth.spring_boot_shoe.utils.SendMailService;
import com.dth.spring_boot_shoe.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private final SendMailService sendMailService;



    @Override
    public void save(UserDTO userDTO, HttpServletRequest request){
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RequestException("Email đã được đăng ký","login");
        }
        RoleEntity role=roleService.findByCode("ROLE_USER");
        UserEntity user=modelMapper.map(userDTO,UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(role));
        user.setProvider(Provider.local);
        user.setSlug(StringUtils.removeAccent(userDTO.getFullName()));
        String randomCode = RandomString.make(64);
        user.setVerificationToken(randomCode);
        user.setEnabled(false);
        userRepository.save(user);
        try {
            sendMailService.verifyCode(user,request);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetVerificationToken(UserEntity user, HttpServletRequest request) {
        String randomCode = RandomString.make(64);
        user.setVerificationToken(randomCode);
        userRepository.save(user);

        try {
            sendMailService.verifyCode(user,request);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verify(String verificationToken) {
        Optional<UserEntity> user = userRepository.findByVerificationToken(verificationToken);
        if(!user.isPresent()){
            return false;
        }else {
            UserEntity entity = user.get();
            if (entity.getEnabled()) {
                return false;
            } else {
                entity.setVerificationToken(null);
                entity.setEnabled(true);
                userRepository.save(entity);
                return true;
            }
        }
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailAndStatusAndEnabled(email,1,true)
                .orElseThrow(()->new ApiRequestException("User này chưa được đăng ký"));
    }

    @Override
    public UserRequest loadUser() {
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),1,true)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập","/login"));
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
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),1,true)
                        .orElseThrow(()->new ApiRequestException("Qua thời gian chờ, hãy đăng nhập lại"));
        String fullName=request.getLastName()+" "+request.getFirstName();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(fullName);
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setSlug(StringUtils.removeAccent(fullName));
        userRepository.save(user);
        Authentication authentication= new PreAuthenticatedAuthenticationToken(CustomOAuth2User.createCustomUser(user),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //khi load page info
    @Override
    public UserEntity profile() {
        return userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),1,true)
                .orElseThrow(()->new RequestException("Bạn chưa đăng nhập tài khoản","/login"));
    }

    //load form detail
    @Override
    public UserEntity profileDetail() {
        return userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),
                1,true).orElseThrow(()->new ApiRequestException("Bạn chưa đăng nhập tài khoản"));
    }

    @Override
    public Map<String,String> changePass(ChangePWRequest request) {
        HashMap<String,String> map=new HashMap<>();
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),
                1,true).orElseThrow(()->new ApiRequestException("Bạn hãy đăng nhập lại tài khoản"));
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
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(SecurityContextHolder.getContext().getAuthentication().getName(),
                1,true).orElseThrow(()->new ApiRequestException("Bạn hãy đăng nhập lại tài khoản"));
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
    public Map<String, Object> findAll(int page) {
        Map<String,Object> map=new HashMap<>();
        Pageable pageable= PageRequest.of(page-1,10);
        Page<UserEntity> entities=userRepository.findByStatus(1,pageable);
        map.put("totalItems",entities.getTotalElements());
        map.put("users",entities.getContent());
        return map;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        UserEntity user=userRepository.findByEmailAndStatusAndEnabled(email,1,true)
                .orElseThrow(()->new UsernameNotFoundException("User này chưa được đăng ký"));
        return CustomOAuth2User.createCustomUser(user);
    }
}
