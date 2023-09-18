package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.persistence.impl.ProductDetailDAOImpl;
import com.project.ecommerce.repository.ProductDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


    @RunWith(MockitoJUnitRunner.class)
    public class ProductDetailDAOImplTest {

        @Mock
        private ProductDetailRepository mockProductDetailRepository;

        @InjectMocks
        private ProductDetailDAOImpl productDetailDAO;

        @Test
        public void findByProductIdTest() {
            Long productId = 1L;
            ProductDetail expectedProductDetail = new ProductDetail();
            when(mockProductDetailRepository.findByProduct_Id(productId)).thenReturn(Optional.of(expectedProductDetail));

            Optional<ProductDetail> result = productDetailDAO.findByProductId(productId);

            assertTrue(result.isPresent());
            assertEquals(expectedProductDetail, result.get());
        }

        @Test
        public void saveTest() {
            ProductDetail productDetailToSave = new ProductDetail();

            productDetailDAO.save(productDetailToSave);

            verify(mockProductDetailRepository).save(productDetailToSave);
        }
    }

