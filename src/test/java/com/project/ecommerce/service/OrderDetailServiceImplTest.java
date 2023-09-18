package com.project.ecommerce.service;

import com.project.ecommerce.entities.OrderDetail;
import com.project.ecommerce.persistence.IOrderDetailDAO;
import com.project.ecommerce.service.impl.OrderDetailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailServiceImplTest {

    @Mock
    private IOrderDetailDAO mockOrderDetailDAO;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Test
    public void saveTest() {
        OrderDetail orderDetailToSave = new OrderDetail();

        orderDetailService.save(orderDetailToSave);

        verify(mockOrderDetailDAO).save(orderDetailToSave);
    }
}