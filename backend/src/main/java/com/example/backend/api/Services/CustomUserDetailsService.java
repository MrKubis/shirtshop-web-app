package com.example.backend.api.Services;

import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.UserRepository;
import com.example.backend.api.Principals.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByUserName(username).isPresent()){
            User user =  userRepository.findByUserName(username).get();
            Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                    .flatMap(role -> role.getAuthorities().stream())
                    .map( authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toSet());
            user.getRoles().forEach(
                    role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()))
            );
            return new UserPrincipal(user);
        }
        else{
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not found");
        }
    }
}
