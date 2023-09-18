package com.project.ecommerce.security.service;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.security.service.impl.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        String username = "ronaldinho";

        UserEntity user2 = UserEntity.builder()
                .email("molina@gmail.com")
                .name("Ramiro")
                .enabled(true)
                .username("ronaldinho")
                .password("password")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.CUSTOMER.name()))
                        .build()))
                .build();

        when(repository.findByUsername(username)).thenReturn(Optional.of(user2));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());

        Set<GrantedAuthority> authorities = new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_UsernameNotFound() {
        // Arrange
        String username = "nonexistentuser";

        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        userDetailsService.loadUserByUsername(username);

        // UsernameNotFoundException should be thrown
    }
}