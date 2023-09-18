package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.ProductDetailDTO;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.service.IProductDetailService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailControllerTest {


        @Mock
        private IProductDetailService productDetailService;
        @InjectMocks
        private ProductDetailController productDetailController;
        @Mock
        BindingResult bindingResult;
        @BeforeEach
        public void SetUp(){
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testSaveProductDetail() {
            // 1. Preparación del Entorno de Prueba
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();
            productDetailDTO.setName("Example Product");
            productDetailDTO.setStock(100);
            productDetailDTO.setPrice(BigDecimal.valueOf(10.0));
            productDetailDTO.setBrand("Example Brand");
            productDetailDTO.setCategory(new Category());
            productDetailDTO.setDescription("Example Description");
            productDetailDTO.setWeight(1.5);
            productDetailDTO.setWarranty("test weeks");
            productDetailDTO.setFabrication(new Date());

            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.hasErrors()).thenReturn(false);

            // 2. Llamada a la Función
            ResponseEntity<?> response = productDetailController.save(productDetailDTO, bindingResult);

            // 3. Verificación
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(productDetailService, times(1)).saveProductAndDetail(any(ProductDetail.class), any(Product.class));
        }

    @Test
    public void testSaveProductDetail_InvalidFields() {
        // 1. Preparación del Entorno de Prueba
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // 2. Llamada a la Función
        ResponseEntity<?> response = productDetailController.save(productDetailDTO, bindingResult);

        // 3. Verificación
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Check that the fields are valid", response.getBody());
    }

        @Test
        public void testFindDetailProduct() {
            // 1. Preparación del Entorno de Prueba
            Long productId = 1L;
            ProductDetail productDetail = new ProductDetail();
            Optional<ProductDetail> detailOptional = Optional.of(productDetail);
            when(productDetailService.findByProductId(productId)).thenReturn(detailOptional);

            // 2. Llamada a la Función
            ResponseEntity<?> response = productDetailController.findDetailProduct(productId);

            // 3. Verificación
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(detailOptional, response.getBody());
        }

        @Test
        public void testFindDetailProduct_NotFound() {
            // 1. Preparación del Entorno de Prueba
            Long productId = 1L;
            Optional<ProductDetail> detailOptional = Optional.empty();
            when(productDetailService.findByProductId(productId)).thenReturn(detailOptional);

            // 2. Llamada a la Función
            ResponseEntity<?> response = productDetailController.findDetailProduct(productId);

            // 3. Verificación
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        }
    }

