package com.dth.spring_boot_shoe.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dth.spring_boot_shoe.security.user.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private String jwtSecret = "hieudt@gmail.com";
    private int jwtExpiration=86400;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManagerBean) {
        this.authenticationManager=authenticationManagerBean;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        log.info("Username is: {}",email);
        log.info("Password id: {}",password);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User= (CustomOAuth2User) authentication.getPrincipal();
        String token= JWT.create()
                .withSubject(customOAuth2User.getUsername())
                .withExpiresAt(new Date(new Date().getTime()+jwtExpiration*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",customOAuth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
        response.setHeader("token",token);
    }
}
