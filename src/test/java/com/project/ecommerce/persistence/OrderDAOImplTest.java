package com.project.ecommerce.persistence;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.project.ecommerce.entities.Order;
import com.project.ecommerce.persistence.impl.OrderDAOImpl;
import com.project.ecommerce.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(mockOrderRepository.findAll()).thenReturn(expectedOrders);

        List<Order> result = orderDAO.findAll();

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
