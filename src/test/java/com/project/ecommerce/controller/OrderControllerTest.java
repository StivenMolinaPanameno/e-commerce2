package com.project.ecommerce.controller;

import com.project.ecommerce.entities.Order;
import com.project.ecommerce.exception.NotProducts;
import com.project.ecommerce.service.IOrderService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import  java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    IOrderService orderService;

    @Test
    public void testCreateOrder() throws NotProducts {
        // Mock del contexto de seguridad
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Llamada al controlador
        ResponseEntity<?> response = orderController.createOrder();

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).createOrder("testUser");
    }



    @Test
    @WithMockUser(roles = "CUSTOMER")
    public void createOrderWithExceptionTest() throws NotProducts {
        // Configuración de datos de prueba para el caso de excepción
        String errorMessage = "Add Products";
        doThrow(new NotProducts(errorMessage)).when(orderService).createOrder(anyString());

        // Llamar al método
        ResponseEntity<?> response = orderController.createOrder();

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
    @Test
    public void testGetLastOrder() {
        // Mock del contexto de seguridad
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Mock de la respuesta del servicio
        Order order = new Order();
        Optional<Order> orderOptional = Optional.of(order);
        when(orderService.findFirstByUserUsernameOrderByDateDesc("testUser")).thenReturn(orderOptional);

        // Llamada al controlador
        ResponseEntity<?> response = orderController.getLastOrder();

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderOptional, response.getBody());
    }

    @Test
    public void testGetAllMyOrders() {
        // Mock del contexto de seguridad
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Mock de la respuesta del servicio
        List<Order> orderList = Arrays.asList(new Order(), new Order());
        when(orderService.findActiveOrdersByUsername("testUser")).thenReturn(orderList);

        // Llamada al controlador
        ResponseEntity<?> response = orderController.getAllMyOrders();

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, response.getBody());
    }

    @Test
    public void testDisableOrder() {
        // Llamada al controlador
        ResponseEntity<?> response = orderController.disableOrder(1L);

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).disableOrderById(1L);
    }
}
