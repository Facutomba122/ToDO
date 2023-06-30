package com.Fp2023.ToDo.Configuration;

import com.Fp2023.ToDo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Autowired
    private UserService userService;
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
        http.authorizeHttpRequests(
                (authz) -> authz
                    .anyRequest().permitAll()
        );
        
        http.formLogin(
                (form) -> form
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/loginSendData")
        );
        
        http.logout(
                (logout) -> logout
                    .logoutUrl("/user/logout")
        );
        
        return http.build();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(userService.bCryptPasswordEncoder());
        return auth;
    }
}
