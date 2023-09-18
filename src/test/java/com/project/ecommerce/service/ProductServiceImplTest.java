package com.project.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.IProductDAO;
import com.project.ecommerce.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private IProductDAO mockProductDAO;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void findAllTest() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(mockProductDAO.findAll()).thenReturn(expectedProducts);

        List<Product> result = productService.findAll();

        assertEquals(expectedProducts, result);
    }

    @Test
    public void findByCategoryTest() {
        String category = "TestCategory";
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(mockProductDAO.findByCategory(category)).thenReturn(expectedProducts);

        List<Product> result = productService.findByCategory(category);

        assertEquals(expectedProducts, result);
    }

    @Test
    public void findByIdTest() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        when(mockProductDAO.findByIdAndEnabledTrueAndCategoryEnabledTrue(productId)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
    }

    @Test
    public void findByNameTest() {
        String productName = "TestProduct";
        Product expectedProduct = new Product();
        when(mockProductDAO.findByNameAndEnabledTrueAndCategoryTrue(productName)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = productService.findByNameAndEnabledTrue(productName);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
    }

    @Test
    public void disableByIdTest() {
        Long productId = 1L;

        productService.disableById(productId);

        verify(mockProductDAO).disableById(productId);
    }

    @Test
    public void disableByNameTest() {
        String productName = "TestProduct";

        productService.disableByName(productName);

        verify(mockProductDAO).disableByName(productName);
    }

    @Test
    public void saveTest() {
        Product productToSave = new Product();

        productService.save(productToSave);

        verify(mockProductDAO).save(productToSave);
    }

    @Test
    public void deleteTest() {
        Long productId = 1L;

        productService.delete(productId);

        verify(mockProductDAO).delete(productId);
    }
}
