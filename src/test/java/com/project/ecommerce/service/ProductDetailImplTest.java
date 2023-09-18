package com.project.ecommerce.service;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.persistence.IProductDetailDAO;
import com.project.ecommerce.service.impl.ProductDetailImpl;
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
public class ProductDetailImplTest {

    @Mock
    private IProductDetailDAO mockProductDetailDAO;

    @Mock
    private IProductService mockProductService;

    @InjectMocks
    private ProductDetailImpl productDetailService;

    @Test
    public void findByProductIdTest() {
        Long productId = 1L;
        ProductDetail expectedProductDetail = new ProductDetail();
        when(mockProductDetailDAO.findByProductId(productId)).thenReturn(Optional.of(expectedProductDetail));

        Optional<ProductDetail> result = productDetailService.findByProductId(productId);

        assertTrue(result.isPresent());
        assertEquals(expectedProductDetail, result.get());
    }

    @Test
    public void saveTest() {
        ProductDetail productDetailToSave = new ProductDetail();

        productDetailService.save(productDetailToSave);

        verify(mockProductDetailDAO).save(productDetailToSave);
    }

    @Test
    public void saveProductAndDetailTest() {
        Product productToSave = new Product();
        ProductDetail productDetailToSave = new ProductDetail();

        productDetailService.saveProductAndDetail(productDetailToSave, productToSave);

        verify(mockProductService).save(productToSave);
        verify(mockProductDetailDAO).save(productDetailToSave);
    }
}