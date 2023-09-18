package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.Category;
import com.project.ecommerce.persistence.ICategoryDAO;
import com.project.ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDAOImpl implements ICategoryDAO {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void disableById(Long id) {
        categoryRepository.disableCategoryById(id);
    }

    @Transactional
    @Override
    public void disableByName(String name) {
        categoryRepository.disableCategoryByName(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
