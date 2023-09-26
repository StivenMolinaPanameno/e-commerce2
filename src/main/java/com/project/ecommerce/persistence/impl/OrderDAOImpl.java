package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.Order;
import com.project.ecommerce.persistence.IOrderDAO;
import com.project.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAOImpl implements IOrderDAO {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUser_Username(String username) {
        return orderRepository.findByUser_Username(username);
    }

    @Override
    public Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username) {
        return orderRepository.findFirstByUserUsernameOrderByDateDesc(username);
    }

    @Override
    public Optional<Order> findLastActiveOrderByUsername(String username) {
        return orderRepository.findLastActiveOrderByUsername(username);
    }

    @Transactional
    @Override
    public void disableOrderById(Long orderId) {
        orderRepository.disableOrderById(orderId);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> findActiveOrdersByUsername(String username) {
        return orderRepository.findActiveOrdersByUsername(username);
    }


}
