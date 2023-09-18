package com.project.ecommerce.service;

import com.project.ecommerce.entities.Category;
import com.project.ecommerce.persistence.ICategoryDAO;
import com.project.ecommerce.service.impl.CategoryServiceimpl;
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
public class CategoryServiceImplTest {

    @Mock
    private ICategoryDAO mockCategoryDAO;

    @InjectMocks
    private CategoryServiceimpl categoryService;

    @Test
    public void findAllTest() {
        List<Category> expectedCategories = Arrays.asList(new Category(), new Category());
        when(mockCategoryDAO.findAll()).thenReturn(expectedCategories);

        List<Category> result = categoryService.findAll();

        assertEquals(expectedCategories, result);
    }

    @Test
    public void findByIdTest() {
        Long categoryId = 1L;
        Category expectedCategory = new Category();
        when(mockCategoryDAO.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }

    @Test
    public void saveTest() {
        Category categoryToSave = new Category();

        categoryService.save(categoryToSave);

        verify(mockCategoryDAO).save(categoryToSave);
    }

    @Test
    public void disableByIdTest() {
        Long categoryId = 1L;

        categoryService.disableById(categoryId);

        verify(mockCategoryDAO).disableById(categoryId);
    }

    @Test
    public void disableByNameTest() {
        String categoryName = "TestCategory";

        categoryService.disableByName(categoryName);

        verify(mockCategoryDAO).disableByName(categoryName);
    }

    @Test
    public void findByNameTest() {
        String categoryName = "TestCategory";
        Category expectedCategory = new Category();
        when(mockCategoryDAO.findByName(categoryName)).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryService.findByName(categoryName);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }
}