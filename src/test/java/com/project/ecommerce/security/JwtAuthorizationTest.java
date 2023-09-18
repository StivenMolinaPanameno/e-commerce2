package com.project.ecommerce.security;

import com.project.ecommerce.security.filters.JwtAuthorizationFilter;
import com.project.ecommerce.security.jwt.JwtUtils;
import com.project.ecommerce.security.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

public class JwtAuthorizationTest {

    @Test
    public void testDoFilterInternal() throws IOException, ServletException, java.io.IOException {
        // Mock objetos necesarios
        JwtUtils jwtUtils = mock(JwtUtils.class);
        UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter();
        filter.setJwtUtils(jwtUtils);
        filter.setUserDetailsService(userDetailsService);

        // Mock el HttpServletRequest y HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock el UserDetails
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        // Simula un token válido
        String validToken = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(jwtUtils.isTokenValid(validToken)).thenReturn(true);
        when(jwtUtils.getUsernameFromToken(validToken)).thenReturn("testuser");

        // Ejecuta el método
        filter.doFilterInternal(request, response, mock(FilterChain.class));

        // Verifica que SecurityContextHolder.getContext().setAuthentication fue llamado
        verify(userDetailsService).loadUserByUsername("testuser");
        verify(request).getHeader("Authorization");
        verify(jwtUtils).isTokenValid(validToken);
        verify(jwtUtils).getUsernameFromToken(validToken);
    }
}
