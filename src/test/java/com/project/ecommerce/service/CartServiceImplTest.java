package com.project.ecommerce.service;

import com.project.ecommerce.controller.DTO.CartDTO;
import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.persistence.ICartDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.service.impl.CartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {
    @Mock
    private ICartDAO mockCartDAO;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void findByUserEntityUsernameTest() {
        String username = "testUser";
        List<Cart> expectedCarts = Arrays.asList(new Cart(), new Cart());
        when(mockCartDAO.findByUserEntityUsername(username)).thenReturn(expectedCarts);

        List<Cart> result = cartService.findByUserEntityUsername(username);

        assertEquals(expectedCarts, result);
    }

    @Test
    public void findByUserEntityIdTest() {
        Long userId = 1L;
        List<Cart> expectedCarts = Arrays.asList(new Cart(), new Cart());
        when(mockCartDAO.findByUserEntityId(userId)).thenReturn(expectedCarts);

        List<Cart> result = cartService.findByUserEntityId(userId);

        assertEquals(expectedCarts, result);
    }

    @Test
    public void cleanCartByUserIdTest() {
        Long userId = 1L;

        cartService.cleanCartByUserId(userId);

        verify(mockCartDAO).cleanCartByUserId(userId);
    }

    @Test
    public void countByUserEntityIdTest() {
        Long userId = 1L;
        Long expectedCount = 5L;
        when(mockCartDAO.countByUserEntityId(userId)).thenReturn(expectedCount);

        Long result = cartService.countByUserEntityId(userId);

        assertEquals(expectedCount, result);
    }

    @Test
    public void addProductTest() throws UserNotFoundException {
        String username = "testUser";
        CartDTO cartDTO = new CartDTO(); // Asegúrate de inicializar correctamente el DTO

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // Simula el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "password"));

        // Ejecuta el método a probar
        cartService.addProduct(cartDTO);

        // Verifica que se haya llamado a los métodos correspondientes
        verify(mockUserRepository).findByUsername(username);

        // Asegúrate de que el DTO se convierte en un objeto Cart correctamente
        verify(mockCartDAO).addProduct(any(Cart.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void addProductUserNotFoundTest() throws UserNotFoundException {
        String username = "testUser";
        CartDTO cartDTO = new CartDTO(); // Asegúrate de inicializar correctamente el DTO

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Ejecuta el método a probar (esto debería lanzar una excepción UserNotFoundException)
        cartService.addProduct(cartDTO);
    }

    @Test
    public void deleteCartByIdTest() {
        Long cartId = 1L;

        cartService.deleteCartById(cartId);

        verify(mockCartDAO).deleteCartById(cartId);
    }
}
