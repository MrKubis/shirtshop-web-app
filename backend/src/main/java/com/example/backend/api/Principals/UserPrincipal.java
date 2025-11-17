package com.example.backend.api.Principals;

import com.example.backend.api.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final Set<GrantedAuthority> authorities;
    private User user;
    public UserPrincipal(User user){
        this.user = user;
        Set<GrantedAuthority> auths = user.getRoles().stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toSet());

        user.getRoles().forEach(role -> auths.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId(){return user.getId();}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
