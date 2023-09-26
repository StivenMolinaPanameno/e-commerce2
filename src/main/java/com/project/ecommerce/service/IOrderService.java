package com.project.ecommerce.service;



import com.project.ecommerce.entities.Order;
import com.project.ecommerce.exception.NotProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findByUser_Username(String username);
    Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username);
    Optional<Order> findLastActiveOrderByUsername(String username);
    void disableOrderById( Long orderId);
    Page<Order> findAll(Pageable pageable);
    void createOrder(String Username) throws NotProducts;
    List<Order> findActiveOrdersByUsername(String username);
}
