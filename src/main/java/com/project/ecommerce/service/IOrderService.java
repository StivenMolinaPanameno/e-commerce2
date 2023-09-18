package com.project.ecommerce.service;



import com.project.ecommerce.entities.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findByUser_Username(String username);
    Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username);
    Optional<Order> findLastActiveOrderByUsername(String username);
    void disableOrderById( Long orderId);
    List<Order> findAll();
    void createOrder(String Username);
    List<Order> findActiveOrdersByUsername(String username);
}
