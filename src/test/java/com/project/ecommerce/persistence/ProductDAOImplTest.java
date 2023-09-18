package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.impl.ProductDAOImpl;
import com.project.ecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDAOImplTest {

    @Mock
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ProductDAOImpl productDAO;

    @Test
    public void findAllTest() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(mockProductRepository.findAll()).thenReturn(expectedProducts);

        List<Product> result = productDAO.findAll();

        assertEquals(expectedProducts, result);
    }

    @Test
    public void findByCategoryTest() {
        String category = "TestCategory";
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(mockProductRepository.findByCategoryName(category)).thenReturn(expectedProducts);

        List<Product> result = productDAO.findByCategory(category);

        assertEquals(expectedProducts, result);
    }

    @Test
    public void findByIdTest() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        when(mockProductRepository.findByIdAndEnabledTrueAndCategoryEnabledTrue(productId)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = productDAO.findByIdAndEnabledTrueAndCategoryEnabledTrue(productId);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
    }

    @Test
    public void findByNameTest() {
        String productName = "TestProduct";
        Product expectedProduct = new Product();
        when(mockProductRepository.findByNameAndEnabledTrueAndCategoryEnabledTrue(productName)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = productDAO.findByNameAndEnabledTrueAndCategoryTrue(productName);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
    }

    @Test
    public void disableByIdTest() {
        Long productId = 1L;

        productDAO.disableById(productId);

        verify(mockProductRepository).disableById(productId);
    }

    @Test
    public void disableByNameTest() {
        String productName = "TestProduct";

        productDAO.disableByName(productName);

        verify(mockProductRepository).disableByName(productName);
    }

    @Test
    public void saveTest() {
        Product productToSave = new Product();

        productDAO.save(productToSave);

        verify(mockProductRepository).save(productToSave);
    }

    @Test
    public void deleteTest() {
        Long productId = 1L;

        productDAO.delete(productId);

        verify(mockProductRepository).deleteById(productId);
    }
}