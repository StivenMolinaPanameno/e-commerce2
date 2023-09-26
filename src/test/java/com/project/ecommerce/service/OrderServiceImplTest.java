package com.project.ecommerce.service;

import com.mysql.cj.CacheAdapter;
import com.project.ecommerce.entities.*;
import com.project.ecommerce.persistence.IOrderDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private IOrderDAO mockOrderDAO;

    @Mock
    private IOrderDetailService orderDetailService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testFindByUser_Username() {
        // Configuración de datos de prueba
        String username = "testuser";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        List<Order> mockOrders = Arrays.asList(
                new Order(), new Order(), new Order()
        );


        // Configurar mock de orderDAO
        when(mockOrderDAO.findByUser_Username(username)).thenReturn(mockOrders);

        // Llamar al método que queremos probar
        List<Order> result = orderService.findByUser_Username(username);

        // Verificar que el resultado es el esperado
        assertEquals(mockOrders, result);
    }

    @Test
    public void findFirstByUserUsernameOrderByDateDescTest() {
        String username = "testUser";
        Optional<Order> expectedOrder = Optional.of(new Order());
        when(mockOrderDAO.findFirstByUserUsernameOrderByDateDesc(username)).thenReturn(expectedOrder);

        Optional<Order> result = orderService.findFirstByUserUsernameOrderByDateDesc(username);

        assertEquals(expectedOrder, result);
    }

    @Test
    public void findLastActiveOrderByUsernameTest() {
        String username = "testUser";
        Optional<Order> expectedOrder = Optional.of(new Order());
        when(mockOrderDAO.findLastActiveOrderByUsername(username)).thenReturn(expectedOrder);

        Optional<Order> result = orderService.findLastActiveOrderByUsername(username);

        assertEquals(expectedOrder, result);
    }

    @Test
    public void disableOrderByIdTest() {
        Long orderId = 1L;
        orderService.disableOrderById(orderId);

        verify(mockOrderDAO).disableOrderById(orderId);
    }

    @Test
    public void findAllTest() {
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        Pageable pageable = PageRequest.of(0, 5);

        // Simula la respuesta de una página de órdenes
        Page<Order> page = new PageImpl<>(expectedOrders, pageable, expectedOrders.size());
        when(mockOrderDAO.findAll(pageable)).thenReturn(page);

        Page<Order> resultPage = orderService.findAll(pageable);
        List<Order> result = resultPage.getContent();

        assertEquals(expectedOrders, result);
        assertEquals(expectedOrders.size(), resultPage.getTotalElements());
    }


    @Test
    public void findActiveOrdersByUsernameTest() {
        String username = "testUser";
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(mockOrderDAO.findActiveOrdersByUsername(username)).thenReturn(expectedOrders);

        List<Order> result = orderService.findActiveOrdersByUsername(username);

        assertEquals(expectedOrders, result);
    }

    @Test
    public void createOrderEntityTest() {
        BigDecimal total = BigDecimal.valueOf(100.0);
        UserEntity user = new UserEntity();

        Order order = orderService.createOrderEntity(total, user);

        assertNotNull(order);
        assertEquals(total, order.getTotal());
        assertEquals(user, order.getUser());
        assertTrue(order.getEnable());
    }

    @Test
    public void saveOrderDetailsTest() {
        // Crear un Order
        Order order = new Order();
        order.setId(1L); // Asegúrate de establecer un ID si es necesario

        // Crear un Cart y una lista de shoppingCartList
        Cart cart1 = Cart.builder()
                .amount(4)
                .product(new Product())
                .user(new UserEntity())
                .build();
        Cart cart2 = Cart.builder()
                .amount(3)
                .product(new Product())
                .user(new UserEntity())
                .build();
        List<Cart> shoppingCartList = Arrays.asList(cart1, cart2);

        // Mock del servicio orderDetailService.save()
        doNothing().when(orderDetailService).save(any(OrderDetail.class));

        // Llamar al método que queremos probar
        orderService.saveOrderDetails(order, shoppingCartList);

        // Verificar que el servicio se llamó la cantidad correcta de veces
        verify(orderDetailService, times(shoppingCartList.size())).save(any(OrderDetail.class));

    }
    @Test
    public void calculateTotalTest() {

        Product product = Product.builder()
                .brand("NIKE")
                .price(BigDecimal.valueOf(123.3))
                .name("Shoes")
                .stock(23)
                .category(new Category())
                .enabled(true)
                .build();

        Cart cart1 = Cart.builder()
                .amount(4)
                .product(product)
                .user(new UserEntity())
                .build();
        Cart cart2 = Cart.builder()
                .amount(4)
                .product(product)
                .user(new UserEntity())
                .build();
        List<Cart> shoppingCartList = Arrays.asList(
                cart1, cart2   // Precio: 20, Cantidad: 1
        );

        BigDecimal total = orderService.calculateTotal(shoppingCartList);

        assertEquals(BigDecimal.valueOf(986.4), total);
    }


}
