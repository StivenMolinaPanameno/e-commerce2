package com.project.ecommerce.security;

import com.project.ecommerce.security.filters.JwtAuthenticationFilter;
import com.project.ecommerce.security.filters.JwtAuthorizationFilter;
import com.project.ecommerce.security.jwt.JwtUtils;
import com.project.ecommerce.security.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
     UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtAuthorizationFilter authorizationFilter;
    @Autowired
    JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(config->config.disable())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/hello").permitAll();
                    auth.requestMatchers("createUser").permitAll();
                    auth.anyRequest().authenticated();

                })

                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();

    }


}

