package com.project.ecommerce.security.service;

import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.persistence.IUserRepositoryDAO;
import com.project.ecommerce.security.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private IUserRepositoryDAO repository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testFindByUsername() {
        // 1. Preparación del Entorno de Prueba
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        when(repository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // 2. Llamada a la Función
        Optional<UserEntity> result = userService.findByUsername(username);

        // 3. Verificación
        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        // 1. Preparación del Entorno de Prueba
        String username = "nonExistentUser";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        // 2. Llamada a la Función
        Optional<UserEntity> result = userService.findByUsername(username);

        // 3. Verificación
        assertFalse(result.isPresent());
    }


    @Test
    public void testSave() {
        // Crear un objeto UserEntity para la prueba
        UserEntity user = UserEntity.builder()
                .email("molina@gmail.com")
                .name("Ramiro")
                .enabled(true)
                .username("ronaldinho")
                .password("12345")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.CUSTOMER.name()))
                        .build()))
                .build();

        // Cuando se llame a repository.save(user), no hará nada porque estamos usando un mock
        doNothing().when(repository).save(user);

        // Llamar al método que queremos probar
        userService.save(user);

        // Verificar que se llamó a repository.save(user) exactamente una vez
        verify(repository, times(1)).save(user);
    }


    @Test
    public void testFindAll() {
        UserEntity user = UserEntity.builder()
                .email("molina@gmail.com")
                .name("Ramiro")
                .enabled(true)
                .username("ronaldinho")
                .password("12345")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.CUSTOMER.name()))
                        .build()))
                .build();
        UserEntity user2 = UserEntity.builder()
                .email("molina@gmail.com")
                .name("Ramiro")
                .enabled(true)
                .username("ronaldinho")
                .password("12345")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.CUSTOMER.name()))
                        .build()))
                .build();
        // Crear una lista de usuarios de ejemplo
        List<UserEntity> users = Arrays.asList(
               user, user2
        );

        // Configurar el comportamiento de repository.findAll()
        when(repository.findAll()).thenReturn(users);

        // Llamar al método que queremos probar
        List<UserEntity> result = userService.findAll();

        // Verificar que el resultado sea igual a la lista de usuarios de ejemplo
        assertEquals(users, result);
    }

    @Test
    public void testDisableById() {
        // ID del usuario a deshabilitar
        Long userId = 1L;

        // Llamar al método que queremos probar
        userService.disableById(userId);

        // Verificar que se llamó a repository.disableById(userId) exactamente una vez
        verify(repository, times(1)).disableById(userId);
    }
    @Test
    public void testDisableByUsername() {
        // Nombre de usuario del usuario a deshabilitar
        String username = "ejemploUsuario";

        // Llamar al método que queremos probar
        userService.disableByUsername(username);

        // Verificar que se llamó a repository.disableByUsername(username) exactamente una vez
        verify(repository, times(1)).disableByUsername(username);
    }

    @Test
    public void testDeleteById() {
        // ID del usuario a eliminar
        Long userId = 4L;

        // Llamar al método que queremos probar
        userService.deleteById(userId);

        // Verificar que se llamó a repository.deleteById(userId) exactamente una vez
        verify(repository, times(1)).deleteById(userId);
    }



}


