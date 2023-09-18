package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.OrderDetail;
import com.project.ecommerce.persistence.IOrderDetailDAO;
import com.project.ecommerce.repository.OrderDetailRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailDAO implements IOrderDetailDAO {

   @Autowired
   OrderDetailRepository orderDetailRepository;

   @Transactional
    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
}
