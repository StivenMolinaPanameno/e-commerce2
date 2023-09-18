package com.project.ecommerce.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.filters.JwtAuthenticationFilter;
import com.project.ecommerce.security.jwt.JwtUtils;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;


import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

        @Test
        public void testSuccessfulAuthentication() throws IOException, ServletException, java.io.IOException {
            // Mock objetos necesarios
            JwtUtils jwtUtils = mock(JwtUtils.class);
            JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtils);

            // Mock el HttpServletRequest y HttpServletResponse
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);

            // Mock el Authentication y User
            Authentication authentication = mock(Authentication.class);
            User user = mock(User.class);
            when(authentication.getPrincipal()).thenReturn(user);
            when(user.getUsername()).thenReturn("testuser");

            // Mock el token generado por JwtUtils
            String mockToken = "mockToken";
            when(jwtUtils.generateAccesToken("testuser")).thenReturn(mockToken);

            // Mock el FilterChain
            FilterChain filterChain = mock(FilterChain.class);

            // Aseguramos que el getWriter() no sea nulo
            PrintWriter writer = mock(PrintWriter.class);
            when(response.getWriter()).thenReturn(writer);

            // Ejecuta el método
            filter.successfulAuthentication(request, response, filterChain, authentication);

            // Verificamos que los métodos de HttpServletResponse fueron llamados correctamente
            verify(response).addHeader("Authorization", mockToken);
            verify(writer).write(anyString());
            verify(response).setStatus(HttpServletResponse.SC_OK);
            verify(response).setContentType("application/json");
            verify(writer).flush();
        }

    @Test
    public void testAttemptAuthentication() throws IOException, java.io.IOException {
        // Mock objetos necesarios
        JwtUtils jwtUtils = mock(JwtUtils.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtils);
        filter.setAuthenticationManager(authenticationManager);

        // Mock el HttpServletRequest y HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Crea un UserEntity simulado
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("testpassword");

        // Simula el InputStream con el UserEntity
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new ObjectMapper().writeValueAsString(userEntity).getBytes());
        when(request.getInputStream()).thenReturn(new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        });

        // Ejecuta el método
        Authentication result = filter.attemptAuthentication(request, response);

        // Verifica que el AuthenticationManager fue llamado con el UsernamePasswordAuthenticationToken
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }



}



