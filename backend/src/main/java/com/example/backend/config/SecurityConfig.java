package com.example.backend.config;

import com.example.backend.api.Models.Role;
import com.example.backend.api.Repositories.RoleRepository;
import com.example.backend.api.Services.CustomUserDetailsService;
import com.example.backend.api.Models.User;
import com.example.backend.api.Repositories.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys){
        this.rsaKeys = rsaKeys;
    }

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //AUTHENTICATION FOR JWT
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return http
                .csrf(scrf -> scrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/token", "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/public-token").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("GUEST","USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/order").hasAnyRole("GUEST","USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/auth/token").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/orders/**").hasRole("ADMIN")


                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }
    @Bean
    JwtEncoder jwtEncoder(){
        //JWK - json web key
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return  new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder encoder){
        return args -> {

            //GUEST ROLE - FOR EVERYONE
            Role guestRole = roleRepository.findByName("GUEST")
                    .orElseGet(() -> roleRepository.save(new Role("GUEST")));
            //CREATING ADMIN AND USER ROLE
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN")));
            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER")));

            if(userRepository.findByUserName("admin").isEmpty()){
                User user = User.builder()
                        .userName("admin")
                        .email("admin@example.com")
                        .password(encoder.encode("Zaq12wsx"))
                        .roles(new HashSet<Role>())
                        .build();
                user.getRoles().add(adminRole);

                userRepository.save(user);
                System.out.println("Default admin created");

            }
            if(userRepository.findByUserName("user").isEmpty()){
                User user = User.builder()
                        .userName("user")
                        .email("example@example.com")
                        .password(encoder.encode("Zaq12wsx"))
                        .roles(new HashSet<Role>())
                        .build();
                user.getRoles().add(userRole);
                System.out.println("Default user created");
                userRepository.save(user);
            }
            if(userRepository.findByUserName("guest").isEmpty()){
                User guest = User.builder()
                        .userName("guest")
                        .roles(new HashSet<Role>())
                        .build();
                guest.getRoles().add(guestRole);
                System.out.println("Guest user created");
                userRepository.save(guest);
            }
        };
    }
}
