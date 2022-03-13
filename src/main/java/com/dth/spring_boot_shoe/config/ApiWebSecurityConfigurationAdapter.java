package com.dth.spring_boot_shoe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@Order(1)
public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/api/login-page?accessDenied")
                .and()
                .formLogin()
                .loginPage("/api/login-page").usernameParameter("email")
                .failureUrl("/api/login-fail")
                .defaultSuccessUrl("/api/default")
                .and()
                .logout()
                .logoutUrl("/api/logout-url");
    }
}
