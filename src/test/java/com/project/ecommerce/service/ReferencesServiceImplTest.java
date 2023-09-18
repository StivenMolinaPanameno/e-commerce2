package com.project.ecommerce.service;

import com.project.ecommerce.controller.DTO.ReferenceDTO;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.References;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.persistence.IReferenceDAO;
import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.service.impl.ReferencesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReferencesServiceImplTest {

    @Mock
    private IReferenceDAO mockReferenceDAO;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private IProductService mockProductService;

    @InjectMocks
    private ReferencesServiceImpl referencesService;

    @Test
    public void findAllTest() {
        List<References> expectedReferences = Arrays.asList(new References(), new References());
        when(mockReferenceDAO.findAll()).thenReturn(expectedReferences);

        List<References> result = referencesService.findAll();

        assertEquals(expectedReferences, result);
    }

    @Test
    public void saveTest() throws ProductNotFoundException {
        String username = "testUser";
        Long productId = 1L;
        ReferenceDTO referenceDTO = new ReferenceDTO("Test Reference");

        UserEntity user = UserEntity.builder()
                .email("stiven@gmail.com")
                .name("Stiven")
                .enabled(true)
                .username("stivenmolina")
                .password("password")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.ADMIN.name()))
                        .build()))
                .build();

        Product product = new Product();
        product.setId(productId);
        product.setBrand("NIKE");
        product.setCategory(new Category());
        product.setPrice(BigDecimal.valueOf(243.3));
        product.setStock(123);
        product.setName("Anywhere");
        product.setEnabled(true);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(mockProductService.findById(productId)).thenReturn(Optional.of(product));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "password"));


        referencesService.save(referenceDTO, productId);

        verify(mockReferenceDAO).save(any(References.class));
    }

    @Test(expected = ProductNotFoundException.class)
    public void saveProductNotFoundExceptionTest() throws ProductNotFoundException {
        String username = "testUser";
        Long productId = 1L;
        ReferenceDTO referenceDTO = new ReferenceDTO("Test Reference");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(mockProductService.findById(productId)).thenReturn(Optional.empty());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "password"));

        referencesService.save(referenceDTO, productId);
    }

    @Test
    public void findByProduct_IdTest() {
        Long productId = 1L;
        List<References> expectedReferences = Arrays.asList(new References(), new References());
        when(mockReferenceDAO.findByProduct_Id(productId)).thenReturn(expectedReferences);

        List<References> result = referencesService.findByProduct_Id(productId);

        assertEquals(expectedReferences, result);
    }

}
