package com.project.ecommerce.service.impl;


import com.project.ecommerce.entities.OrderDetail;
import com.project.ecommerce.persistence.IOrderDetailDAO;
import com.project.ecommerce.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    IOrderDetailDAO orderDetailDAO;
    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailDAO.save(orderDetail);
    }
}
