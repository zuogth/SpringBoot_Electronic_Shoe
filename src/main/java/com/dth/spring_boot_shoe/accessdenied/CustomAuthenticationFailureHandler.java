package com.dth.spring_boot_shoe.accessdenied;

import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.UserRepository;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectURL = "";
        String email = request.getParameter("email");
        Optional<UserEntity> user=userRepository.findByEmailAndStatus(email,1);
        if(!user.isPresent()){
            redirectURL = "/login?error";
        }else {
            if(!user.get().getEnabled()){
                userService.resetVerificationToken(user.get(),request);
                redirectURL = "/login?unverify";
            }else {
                redirectURL = "/login?error";
            }
        }
        super.setDefaultFailureUrl(redirectURL);


        super.onAuthenticationFailure(request, response, exception);
    }
}
