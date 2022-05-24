package com.dth.spring_boot_shoe.accessdenied;

import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = "";
        String email = request.getParameter("email");
        Optional<UserEntity> userEntity=userRepository.findByEmailAndStatusAndEnabled(email,1,true);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            for(int i=0; i<user.getRoles().size();i++){
                if(user.getRoles().get(i).getCode().equals("ROLE_ADMIN")){
                    redirectURL = "/admin";
                    break;
                }
                if(user.getRoles().get(i).getCode().equals("ROLE_USER")){
                    redirectURL = "/";
                }
            }
        }else {
            redirectURL = "/login?error";
        }

        super.setDefaultTargetUrl(redirectURL);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
