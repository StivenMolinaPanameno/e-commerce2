package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.Category;
import com.project.ecommerce.persistence.impl.CategoryDAOImpl;
import com.project.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryDAOImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    @InjectMocks
    private CategoryDAOImpl categoryDAO;

    @Test
    public void findAllTest() {
        List<Category> expectedCategories = Arrays.asList(new Category(), new Category());
        when(mockCategoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> result = categoryDAO.findAll();

        assertEquals(expectedCategories, result);
    }

    @Test
    public void findByIdTest() {
        Long categoryId = 1L;
        Category expectedCategory = new Category();
        when(mockCategoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryDAO.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }

    @Test
    public void saveTest() {
        Category categoryToSave = new Category();

        categoryDAO.save(categoryToSave);

        verify(mockCategoryRepository).save(categoryToSave);
    }

    @Test
    public void disableByIdTest() {
        Long categoryId = 1L;

        categoryDAO.disableById(categoryId);

        verify(mockCategoryRepository).disableCategoryById(categoryId);
    }

    @Test
    public void disableByNameTest() {
        String categoryName = "TestCategory";

        categoryDAO.disableByName(categoryName);

        verify(mockCategoryRepository).disableCategoryByName(categoryName);
    }

    @Test
    public void findByNameTest() {
        String categoryName = "TestCategory";
        Category expectedCategory = new Category();
        when(mockCategoryRepository.findByName(categoryName)).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryDAO.findByName(categoryName);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }
}
