package com.project.ecommerce.persistence;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.project.ecommerce.entities.Order;
import com.project.ecommerce.persistence.impl.OrderDAOImpl;
import com.project.ecommerce.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderDAOImplTest {

    @Mock
    private OrderRepository mockOrderRepository;

    @InjectMocks
    private OrderDAOImpl orderDAO;

    @Test
    public void saveTest() {
        Order orderToSave = new Order();
        when(mockOrderRepository.save(orderToSave)).thenReturn(orderToSave);

        Order result = orderDAO.save(orderToSave);

        assertEquals(orderToSave, result);
    }

    @Test
    public void findByUser_UsernameTest() {
        String username = "testUser";
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(mockOrderRepository.findByUser_Username(username)).thenReturn(expectedOrders);

        List<Order> result = orderDAO.findByUser_Username(username);

        assertEquals(expectedOrders, result);
    }

    @Test
    public void findFirstByUserUsernameOrderByDateDescTest() {
        String username = "testUser";
        Optional<Order> expectedOrder = Optional.of(new Order());
        when(mockOrderRepository.findFirstByUserUsernameOrderByDateDesc(username)).thenReturn(expectedOrder);

        Optional<Order> result = orderDAO.findFirstByUserUsernameOrderByDateDesc(username);

        assertEquals(expectedOrder, result);
    }

    @Test
    public void findLastActiveOrderByUsernameTest() {
        String username = "testUser";
        Optional<Order> expectedOrder = Optional.of(new Order());
        when(mockOrderRepository.findLastActiveOrderByUsername(username)).thenReturn(expectedOrder);

        Optional<Order> result = orderDAO.findLastActiveOrderByUsername(username);

        assertEquals(expectedOrder, result);
    }

    @Test
    public void disableOrderByIdTest() {
        Long orderId = 1L;

        orderDAO.disableOrderById(orderId);

        verify(mockOrderRepository).disableOrderById(orderId);
    }

    @Test
    public void findAllTest() {
        // Preparar datos de prueba
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        Pageable pageable = PageRequest.of(1, 5);

        // Mock del repositorio
        when(mockOrderRepository.findAll(pageable)).thenReturn(new PageImpl<>(expectedOrders, pageable, expectedOrders.size()));

        // Llamar al método que quieres probar
        Page<Order> resultPage = orderDAO.findAll(pageable);
        List<Order> result = resultPage.getContent();

        // Verificar el resultado
        assertEquals(expectedOrders, result);
    }

    @Test
    public void findActiveOrdersByUsernameTest() {
        String username = "testUser";
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(mockOrderRepository.findActiveOrdersByUsername(username)).thenReturn(expectedOrders);

        List<Order> result = orderDAO.findActiveOrdersByUsername(username);

        assertEquals(expectedOrders, result);
    }
}
