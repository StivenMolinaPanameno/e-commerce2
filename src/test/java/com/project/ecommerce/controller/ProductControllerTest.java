package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.ProductDTO;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;
    @Mock
    BindingResult bindingResult;

    @Test
    public void testFindById_ProductFound() {
        // Configuración de datos de prueba
        Long productId = 1L;
        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        product.setId(productId);

        // Simular que el producto se encuentra
        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Llamar al método findById
        ResponseEntity<?> response = productController.findById(productId);

        // Verificar que se haya retornado un ResponseEntity con estado OK y el DTO del producto
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ProductDTO);

    }

    @Test
    public void testFindById_ProductNotFound() {
        // Configuración de datos de prueba
        Long productId = 1L;

        // Simular que el producto no se encuentra
        when(productService.findById(productId)).thenReturn(Optional.empty());

        // Llamar al método findById
        ResponseEntity<?> response = productController.findById(productId);

        // Verificar que se haya retornado un ResponseEntity con estado BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSave_ValidInput() throws URISyntaxException {
        // Configuración de datos de prueba
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 1");

        // Simular que el producto se guarda correctamente
        doNothing().when(productService).save(any(Product.class));

        // Llamar al método save
        ResponseEntity<?> response = productController.save(productDTO, bindingResult);

        // Verificar que se haya retornado un ResponseEntity con estado CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/v1/products", response.getHeaders().getLocation().getPath());
    }

    @Test
    public void testFindName() {
        // Mock de la respuesta del servicio
        String productName = "TEST";
        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();


        product.setName(productName);
        when(productService.findByNameAndEnabledTrue(productName)).thenReturn(Optional.of(product));

        // Llamada al controlador
        ResponseEntity<?> response = productController.findName(productName);

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAll() {
        // Mock de la respuesta del servicio
        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        List<Product> productList = Arrays.asList(product, product);
        when(productService.findAll()).thenReturn(productList);

        // Llamada al controlador
        ResponseEntity<?> response = productController.findAll();

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllWithPage() {
        // Mock de la respuesta del servicio
        Pageable pageable = PageRequest.of(0, 5);
        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product, product), pageable, 2);
        when(productService.findAllByEnabledTrue(pageable)).thenReturn(productPage);

        // Llamada al controlador
        ResponseEntity<?> response = productController.findAll(0);

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindByCategory() {
        // Mock de la respuesta del servicio
        String categoryName = "Example Category";
        Pageable pageable = PageRequest.of(0, 5);
        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product, product), pageable, 2);
        when(productService.findByCategoryName(categoryName, pageable)).thenReturn(productPage);

        // Llamada al controlador
        ResponseEntity<?> response = productController.findByCategory(categoryName, 0);

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testUpdate() {
        // Mock de la respuesta del servicio
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Name");
        productDTO.setCategory(new Category());
        productDTO.setPrice(BigDecimal.valueOf(20.0));
        productDTO.setEnabled(true);
        productDTO.setStock(100);
        productDTO.setBrand("Updated Brand");

        Product product = Product.builder()
                .enabled(true)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Llamada al controlador
        ResponseEntity<?> response = productController.update(productId, productDTO);

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getCategory(), product.getCategory());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getEnabled(), product.getEnabled());
        assertEquals(productDTO.getStock(), product.getStock());
        assertEquals(productDTO.getBrand(), product.getBrand());
    }

    @Test
    public void testDisableById() {
        // Mock de la respuesta del servicio
        Long productId = 1L;
        Product product = Product.builder()
                .enabled(false)
                .category(new Category())
                .stock(23)
                .name("TEST")
                .price(BigDecimal.valueOf(234.3))
                .brand("Brand")
                .build();
        when(productService.findById(product.getId())).thenReturn(Optional.of(product));

        // Llamada al controlador
        ResponseEntity<?> response = productController.disableById(product.getId());

        // Verificación del resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(product.getEnabled());
    }

    @Test
    public void testConvertToDto() {
        // Crear un objeto Product para usar como entrada
        Product product = new Product();
        product.setName("Example Product");
        product.setBrand("Example Brand");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setStock(100);
        product.setCategory(new Category());
        product.setEnabled(true);

        // Llamar a la función de conversión
        ProductDTO productDTO = productController.convertToDto(product);

        // Verificar si los campos fueron mapeados correctamente
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getBrand(), productDTO.getBrand());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getStock(), productDTO.getStock());
        assertEquals(product.getCategory(), productDTO.getCategory());
        assertEquals(product.getEnabled(), productDTO.getEnabled());
    }

    @Test
    public void testConvertToEntity() {
        // Crear un objeto ProductDTO para usar como entrada
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Example Product");
        productDTO.setBrand("Example Brand");
        productDTO.setPrice(BigDecimal.valueOf(10.0));
        productDTO.setStock(100);
        productDTO.setCategory(new Category());
        productDTO.setEnabled(true);

        // Llamar a la función de conversión
        Product product = productController.convertToEntity(productDTO);

        // Verificar si los campos fueron mapeados correctamente
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getBrand(), product.getBrand());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getStock(), product.getStock());
        assertEquals(productDTO.getCategory(), product.getCategory());
        assertEquals(productDTO.getEnabled(), product.getEnabled());
    }
}

