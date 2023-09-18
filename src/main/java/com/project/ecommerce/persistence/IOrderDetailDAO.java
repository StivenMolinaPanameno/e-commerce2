package com.project.ecommerce.persistence;


import com.project.ecommerce.entities.OrderDetail;

public interface IOrderDetailDAO {
    void save(OrderDetail orderDetail);
}
