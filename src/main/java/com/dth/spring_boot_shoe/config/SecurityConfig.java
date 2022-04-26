package com.dth.spring_boot_shoe.config;

import com.dth.spring_boot_shoe.accessdenied.CustomAccessDeniedHandler;
import com.dth.spring_boot_shoe.accessdenied.CustomAuthenticationFailureHandler;
import com.dth.spring_boot_shoe.accessdenied.CustomAuthenticationProvider;
import com.dth.spring_boot_shoe.security.service.CustomOAuth2UserService;
import com.dth.spring_boot_shoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

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

    @Configuration
    @Order(2)
    @RequiredArgsConstructor
    public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{

        private final UserService userService;
        private final CustomOAuth2UserService customOAuth2UserService;

        @Bean
        public BCryptPasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/**").authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/info/**","/delivery/**").authenticated()
                    .and()
                    .exceptionHandling().accessDeniedPage("/login?accessDenied")
                    .and()
                    .formLogin()
                    .loginPage("/login").usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                    .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);
        }
    }

}
