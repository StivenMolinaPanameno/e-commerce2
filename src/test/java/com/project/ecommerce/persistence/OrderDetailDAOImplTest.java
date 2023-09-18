package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.OrderDetail;
import com.project.ecommerce.persistence.impl.OrderDetailDAO;
import com.project.ecommerce.repository.OrderDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailDAOImplTest {

    @Mock
    private OrderDetailRepository mockOrderDetailRepository;

    @InjectMocks
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void saveTest() {
        OrderDetail orderDetailToSave = new OrderDetail();

        orderDetailDAO.save(orderDetailToSave);

        verify(mockOrderDetailRepository).save(orderDetailToSave);
    }
}