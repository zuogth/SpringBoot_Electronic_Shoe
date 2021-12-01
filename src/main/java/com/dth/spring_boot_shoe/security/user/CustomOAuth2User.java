package com.dth.spring_boot_shoe.security.user;

import com.dth.spring_boot_shoe.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomOAuth2User implements UserDetails, OAuth2User {
    private String email;

    private String password;

    @Getter
    @Setter
    private String fullName;

    private final Collection<? extends GrantedAuthority> authorities;

    @Setter
    private Map<String, Object> attributes;

    public static CustomOAuth2User createCustomUser(UserEntity user,Map<String,Object> attributes){
        List<GrantedAuthority> authorities=Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomOAuth2User(user.getEmail(),user.getPassword(),user.getFullName(),
                authorities,attributes);
    }


    public static CustomOAuth2User createCustomUser(UserEntity user){
        List<GrantedAuthority> authorities= user.getRoles().stream().
                map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
        return new CustomOAuth2User(user.getEmail(),user.getPassword(),user.getFullName(),
                authorities,null);
    }

    @Override
    public String getName() {
        return this.fullName;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
