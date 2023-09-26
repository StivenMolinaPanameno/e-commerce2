package com.project.ecommerce.controller;

import com.project.ecommerce.security.controller.PrincipalController;
import com.project.ecommerce.security.controller.dto.CreateUserDTO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.RoleRepository;
import com.project.ecommerce.security.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PrincipalControllerTest {

        @Mock
        private IUserService userService;

        @Mock
        RoleRepository roleRepository;
        @Mock
    PasswordEncoder passwordEncoder;
        @InjectMocks
        private PrincipalController principalController;

    @Test
    public void testCreateUser() {
        // 1. Preparación del Entorno de Prueba
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("testUser");
        createUserDTO.setPassword("testPassword");
        createUserDTO.setEmail("test@example.com");
        createUserDTO.setName("Test User");
        createUserDTO.setRoles(Set.of("CUSTOMER"));

        // 2. Configuración del Comportamiento del Mock
        UserEntity userEntity = new UserEntity();
        doNothing().when(userService).save(any(UserEntity.class));

        // 3. Llamada a la Función
        ResponseEntity<?> response = principalController.createUser(createUserDTO);

        // 4. Verificación
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).save(any(UserEntity.class));
    }

        @Test
        public void testDeleteUser() {
            // 1. Preparación del Entorno de Prueba
            Long userId = 1L;

            // 2. Llamada a la Función
            ResponseEntity<?> response = principalController.delete(userId);

            // 3. Verificación
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("User deleted", response.getBody());
            verify(userService, times(1)).deleteById(userId);
        }

        @Test
        public void testDeleteUser_UserNotFound() {
            // 1. Preparación del Entorno de Prueba
            Long userId = 1L;
            doThrow(new EmptyResultDataAccessException(1)).when(userService).deleteById(userId);

            // 2. Llamada a la Función
            ResponseEntity<?> response = principalController.delete(userId);

            // 3. Verificación
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(null, response.getBody());
        }

        @Test
        public void testDisableUsername() {
            // 1. Preparación del Entorno de Prueba
            String username = "testUser";

            // 2. Llamada a la Función
            ResponseEntity<?> response = principalController.disableUsername(username);

            // 3. Verificación
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("User disable successful", response.getBody());
            verify(userService, times(1)).disableByUsername(username);
        }

        @Test
        public void testDisableUsername_UserNotFound() {
            // 1. Preparación del Entorno de Prueba
            String username = "testUser";
            doThrow(new UsernameNotFoundException("User not found")).when(userService).disableByUsername(username);

            // 2. Llamada a la Función
            ResponseEntity<?> response = principalController.disableUsername(username);

            // 3. Verificación
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("User Not Found", response.getBody());
        }
}
