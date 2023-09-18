package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.CategoryDTO;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.service.ICategoryService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testSaveCategory() throws URISyntaxException {
        // Configura el DTO y el servicio para simular el guardado
        CategoryDTO categoryDTO = new CategoryDTO("Example Category", true);

        ResponseEntity<?> responseEntity = categoryController.saveCategory(categoryDTO);

        assertEquals(ResponseEntity.created(new URI("api/maker/save")).build(), responseEntity);
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;
        CategoryDTO updatedCategoryDTO = new CategoryDTO("Updated Category", false);

        when(categoryService.findById(categoryId)).thenReturn(Optional.of(new Category()));

        ResponseEntity<?> responseEntity = categoryController.updateCategory(categoryId, updatedCategoryDTO);

        assertEquals(ResponseEntity.ok("Update Successful"), responseEntity);
    }

    @Test
    public void testUpdateAttribute() {
        Long categoryId = 1L;
        boolean newValue = false;

        when(categoryService.findById(categoryId)).thenReturn(Optional.of(new Category()));

        ResponseEntity<?> responseEntity = categoryController.updateAttribute(categoryId, newValue);

        assertEquals(ResponseEntity.ok("Successful"), responseEntity);
    }

    @Test
    public void testDisableById() {
        Long categoryId = 1L;

        when(categoryService.findById(categoryId)).thenReturn(Optional.of(new Category()));

        ResponseEntity<?> responseEntity = categoryController.enabled(categoryId);

        assertEquals(ResponseEntity.ok("Category Disable"), responseEntity);
    }

    @Test
    public void testDisableByName() {
        String categoryName = "Example Category";

        when(categoryService.findByName(categoryName)).thenReturn(Optional.of(new Category()));

        ResponseEntity<?> responseEntity = categoryController.enabled(categoryName);

        assertEquals(ResponseEntity.ok("Category Disable"), responseEntity);
    }
}
