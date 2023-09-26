package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface IOrderDAO {
    Order save(Order order);
    List<Order> findByUser_Username(String username);
    Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username);
    Optional<Order> findLastActiveOrderByUsername(String username);
    void disableOrderById( Long orderId);
    Page<Order> findAll(Pageable pageable);
    List<Order> findActiveOrdersByUsername(@Param("username") String username);

}
