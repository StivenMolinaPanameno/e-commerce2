package com.project.ecommerce.service.impl;


import com.project.ecommerce.entities.Category;
import com.project.ecommerce.persistence.ICategoryDAO;
import com.project.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceimpl implements ICategoryService {

    @Autowired
    ICategoryDAO categoryDAO;
    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public void disableById(Long id) {
        categoryDAO.disableById(id);
    }

    @Override
    public void disableByName(String name) {
        categoryDAO.disableByName(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryDAO.findByName(name);
    }
}
