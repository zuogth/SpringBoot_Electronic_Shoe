package com.dth.spring_boot_shoe.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserInfo implements OAuth2User {

    private OAuth2User auth2User;

    public OAuth2UserInfo(OAuth2User auth2User){
        this.auth2User=auth2User;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return auth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return auth2User.getAttribute("name");
    }

    public String getFirstName(){
        return auth2User.getAttribute("given_name");
    }

    public String getLastName(){
        return auth2User.getAttribute("family_name");
    }

    public String getEmail(){
        return auth2User.getAttribute("email");
    }
}
